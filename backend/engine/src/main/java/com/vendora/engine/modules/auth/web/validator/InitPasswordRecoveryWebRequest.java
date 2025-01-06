package com.vendora.engine.modules.auth.web.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.auth.request.InitPasswordRecoveryRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitPasswordRecoveryWebRequest implements RequestAdapter<InitPasswordRecoveryRequest> {
  @NotBlank(message = "Email cannot be missing or empty")
  @Email(message = "Must be a well-formed email address")
  private String email;

  @Override
  public Class<InitPasswordRecoveryRequest> getTargetClass() {
    return InitPasswordRecoveryRequest.class;
  }
}
