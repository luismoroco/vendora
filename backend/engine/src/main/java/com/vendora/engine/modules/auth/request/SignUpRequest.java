package com.vendora.engine.modules.auth.request;

import com.vendora.engine.modules.user.model.UserType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
  private UserType userType;
  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;
}
