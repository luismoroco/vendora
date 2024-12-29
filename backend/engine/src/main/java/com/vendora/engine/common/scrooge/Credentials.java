package com.vendora.engine.common.scrooge;

import lombok.Setter;

public abstract class Credentials {
  @Setter
  private String type;

  public String getType() {
    return type;
  }
}
