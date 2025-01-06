package com.vendora.engine.modules.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformPasswordRecoveryRequest {
  private String newPassword;
  private String token;
}
