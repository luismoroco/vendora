package com.vendora.engine.modules.user.database;

import com.vendora.engine.modules.user.dao.UserDao;
import com.vendora.engine.modules.user.database.user.UserEntity;
import com.vendora.engine.modules.user.database.user.UserRepository;
import com.vendora.engine.modules.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("postgresql")
public class UserDaoPg implements UserDao {
  private final UserRepository repository;
  private final ModelMapper mapper;

  public UserDaoPg(
    UserRepository repository,
    ModelMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Optional<User> findUserByUsername(String username) {
    return this.repository.findUserEntityByUsername(username).map(UserEntity::toModel);
  }

  @Override
  public boolean userExistByUsername(String username) {
    return this.repository.existsByUsername(username);
  }

  @Override
  public User saveUser(User user) {
    var userEntity = mapper.map(user, UserEntity.class);
    return this.repository.save(userEntity).toModel();
  }
}
