package com.vendora.engine.modules.auth;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.auth.presenter.AuthPresenter;
import com.vendora.engine.modules.auth.request.LoginRequest;
import com.vendora.engine.modules.auth.request.PasswordRecoveryRequest;
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

  public AuthUseCase(
    @Qualifier("postgresql") UserDao userDao,
    @Qualifier("BCrypt") PasswordEncoder passwordEncoder,
    @Qualifier("kafka") AuthPresenter presenter
  ) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
    this.presenter = presenter;
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

  public void passwordRecovery(final PasswordRecoveryRequest request) {
    var user = this.userDao.findUserByEmail(request.getEmail())
      .orElseThrow(
        () -> new NotFoundException("User not found")
      );

    this.presenter.notifyPasswordRecovery(user.getUserId());
  }
}
