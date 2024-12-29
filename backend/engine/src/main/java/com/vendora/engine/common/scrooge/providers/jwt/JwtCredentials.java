package com.vendora.engine.common.scrooge.providers.jwt;

import com.vendora.engine.common.scrooge.Credentials;
import lombok.Getter;

@Getter
public class JwtCredentials extends Credentials {
  private final String token;

  public JwtCredentials(String token) {
    this.setType("JWT");
    this.token = token;
  }
}
