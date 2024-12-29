package com.vendora.engine.modules.user.database.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findUserEntityByUsername(String username);

  boolean existsByUsername(String username);
}
