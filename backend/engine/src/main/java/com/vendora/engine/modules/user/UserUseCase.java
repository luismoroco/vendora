package com.vendora.engine.modules.user;

import com.vendora.engine.modules.user.dao.UserDao;
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
}
