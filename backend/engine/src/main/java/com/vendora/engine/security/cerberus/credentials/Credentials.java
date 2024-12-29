package com.vendora.engine.security.cerberus.credentials;

import lombok.Setter;

public abstract class Credentials {
  @Setter private String type;

  public String getType() {
    return type;
  }
}
