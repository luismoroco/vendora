package com.vendora.engine.common.session.model;

import com.vendora.engine.modules.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser implements Serializable {
  private Long userId;
  private UserType userType;
  private String email;
}
