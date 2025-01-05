package com.vendora.engine.modules.user;

import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.user.dao.UserDao;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.modules.user.request.GetUserByIdRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserUseCase {
  private final UserDao dao;

  public UserUseCase(
    @Qualifier("postgresql") UserDao dao
  ) {
    this.dao = dao;
  }

  public User getUserById(final GetUserByIdRequest request) {
    return this.dao.findUserById(request.getUserId())
      .orElseThrow(
        () -> new NotFoundException("User not found")
      );
  }
}
