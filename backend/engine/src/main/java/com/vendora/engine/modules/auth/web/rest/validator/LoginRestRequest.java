package com.vendora.engine.modules.auth.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.auth.request.LoginRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRestRequest implements RequestAdapter<LoginRequest> {
  @NotBlank(message = "Email cannot be missing or empty")
  @Email(message = "Must be a well-formed email address")
  String username;

  @NotBlank(message = "Password cannot be missing or empty")
  String password;

  @Override
  public Class<LoginRequest> getTargetClass() {
    return LoginRequest.class;
  }
}
