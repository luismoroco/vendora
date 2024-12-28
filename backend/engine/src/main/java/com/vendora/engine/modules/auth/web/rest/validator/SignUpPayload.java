package com.vendora.engine.modules.auth.web.rest.validator;

import com.vendora.engine.common.request.Request;
import com.vendora.engine.modules.auth.request.SignUpRequest;
import com.vendora.engine.modules.user.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignUpPayload extends Request<SignUpRequest> {
  @NotNull
  UserType userType;

  @NotBlank(message = "FirstName cannot be missing or empty")
  String firstName;

  @NotBlank(message = "LastName cannot be missing or empty")
  String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Must be a well-formed email address")
  String email;

  @NotBlank(message = "Email cannot be missing or empty")
  @Email(message = "Must be a well-formed email address")
  String username;

  @NotBlank(message = "Password cannot be missing or empty")
  String password;
}
