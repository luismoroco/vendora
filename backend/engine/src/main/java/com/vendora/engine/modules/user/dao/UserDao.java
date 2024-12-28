package com.vendora.engine.modules.user.dao;

import com.vendora.engine.modules.user.model.User;

import java.util.Optional;

public interface UserDao {
  Optional<User> findUserByUsername(String username);
  boolean userExistByUsername(String username);
  User saveUser(User user);
}
