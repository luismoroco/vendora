package com.vendora.engine.security.cerberus.credentials.types;

import com.vendora.engine.security.cerberus.credentials.Credentials;
import lombok.Getter;

@Getter
public class JwtCredentials extends Credentials {
  private final String token;

  public JwtCredentials(String token) {
    this.setType("JWT");
    this.token = token;
  }
}
