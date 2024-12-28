package com.vendora.engine.modules.auth.model;

import com.vendora.engine.modules.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
  String token;
  Long userId;
  UserType userType;
}
