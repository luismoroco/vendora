package com.vendora.engine.modules.auth.request;

import com.vendora.engine.modules.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  UserType userType;
  String firstName;
  String lastName;
  String email;
  String username;
  String password;
}
