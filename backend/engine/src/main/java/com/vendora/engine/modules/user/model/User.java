package com.vendora.engine.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
  @JsonIgnore
  Long userId;
  UserType userType;
  String firstName;
  String lastName;
  String email;
  String username;
  @JsonIgnore
  String password;
}
