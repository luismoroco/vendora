package com.vendora.engine.modules.auth;

import com.vendora.engine.common.cache.CacheService;
import com.vendora.engine.common.cache.model.CacheTopic;
import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.common.jwt.JwtService;
import com.vendora.engine.modules.auth.presenter.AuthPresenter;
import com.vendora.engine.modules.auth.request.LoginRequest;
import com.vendora.engine.modules.auth.request.InitPasswordRecoveryRequest;
import com.vendora.engine.modules.auth.request.PerformPasswordRecoveryRequest;
import com.vendora.engine.modules.auth.request.SignUpRequest;
import com.vendora.engine.modules.user.dao.UserDao;
import com.vendora.engine.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthUseCase {
  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;
  private final AuthPresenter presenter;
  private final JwtService jwtService;
  private final CacheService cacheService;

  public AuthUseCase(
    @Qualifier("postgresql") UserDao userDao,
    @Qualifier("BCrypt") PasswordEncoder passwordEncoder,
    @Qualifier("kafka") AuthPresenter presenter,
    JwtService jwtService,
    CacheService cacheService
  ) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
    this.presenter = presenter;
    this.jwtService = jwtService;
    this.cacheService = cacheService;
  }

  public User logIn(final LoginRequest request) {
    return this.userDao.findUserByUsername(request.getUsername())
      .orElseThrow(
        () -> new NotFoundException("User not found")
      );
  }

  @Transactional
  public User signUp(final SignUpRequest request) {
    if (this.userDao.userExistByUsername(request.getUsername())) {
      throw new BadRequestException("Username already exist");
    }

    return this.userDao.saveUser(
      User.builder()
        .userType(request.getUserType())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .username(request.getUsername())
        .password(this.passwordEncoder.encode(request.getPassword()))
        .build()
    );
  }

  public void initPasswordRecovery(final InitPasswordRecoveryRequest request) {
    var user = this.userDao.findUserByEmail(request.getEmail())
      .orElseThrow(
        () -> new NotFoundException("User not found")
      );

    this.presenter.notifyPasswordRecovery(user.getUserId());
  }

  public void performPasswordRecovery(final PerformPasswordRecoveryRequest request) {
    var email = this.jwtService.getSubject(request.getToken());
    this.cacheService.get(CacheTopic.PASSWORD_RECOVERY, email, String.class)
      .orElseThrow(
        () -> new BadRequestException("Invalid token")
      );

    var user = this.userDao.findUserByEmail(email)
      .orElseThrow(
        () -> new NotFoundException("User not found")
      );
    user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));

    this.userDao.saveUser(user);
    this.cacheService.evict(CacheTopic.PASSWORD_RECOVERY, email);
  }
}
