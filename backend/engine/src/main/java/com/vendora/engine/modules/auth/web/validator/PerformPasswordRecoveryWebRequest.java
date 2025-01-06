package com.vendora.engine.modules.auth.web.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.auth.request.PerformPasswordRecoveryRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformPasswordRecoveryWebRequest implements RequestAdapter<PerformPasswordRecoveryRequest> {
  @NotBlank
  private String newPassword;

  @NotBlank
  private String token;

  @Override
  public Class<PerformPasswordRecoveryRequest> getTargetClass() {
    return PerformPasswordRecoveryRequest.class;
  }
}
