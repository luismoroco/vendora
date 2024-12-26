package com.vendora.engine.modules.user.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class User {
  Long userId;
  UserType userType;
  String firstName;
  String lastName;
  String email;
  String username;
  String password;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
