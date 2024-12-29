package com.vendora.engine.modules.auth;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.auth.request.LoginRequest;
import com.vendora.engine.modules.auth.request.SignUpRequest;
import com.vendora.engine.modules.user.dao.UserDao;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.modules.user.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthUseCase.class);
  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;

  public AuthUseCase(
    @Qualifier("postgresql") UserDao userDao,
    @Qualifier("BCrypt") PasswordEncoder passwordEncoder
  ) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }

  public User logIn(LoginRequest request) {
    return this.userDao.findUserByUsername(request.getUsername())
      .orElseThrow(
        () -> new NotFoundException("User not found")
      );
  }

  @Transactional
  public User signUp(SignUpRequest request) {
    if (this.userDao.userExistByUsername(request.getUsername())) {
      LOGGER.warn("Username already exist [username=%s]".formatted(request.getUsername()));

      throw new BadRequestException("Username already exist");
    }

    return this.userDao.saveUser(
      User.builder()
        .userType(UserType.CLIENT)
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .username(request.getUsername())
        .password(this.passwordEncoder.encode(request.getPassword()))
        .build()
    );
  }
}
