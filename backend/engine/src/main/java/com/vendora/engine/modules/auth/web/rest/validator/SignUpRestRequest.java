package com.vendora.engine.modules.auth.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.auth.request.SignUpRequest;
import com.vendora.engine.modules.user.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRestRequest implements RequestAdapter<SignUpRequest> {
  @NotNull
  private UserType userType;

  @NotBlank(message = "FirstName cannot be missing or empty")
  private String firstName;

  @NotBlank(message = "LastName cannot be missing or empty")
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Must be a well-formed email address")
  private String email;

  @NotBlank(message = "Email cannot be missing or empty")
  @Email(message = "Must be a well-formed email address")
  private String username;

  @NotBlank(message = "Password cannot be missing or empty")
  private String password;

  @Override
  public Class<SignUpRequest> getTargetClass() {
    return SignUpRequest.class;
  }
}
