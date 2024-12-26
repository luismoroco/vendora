package com.vendora.engine.common.exc;

import lombok.Getter;

@Getter
public class VendoraException extends RuntimeException {
  private final String message;

  public VendoraException(String message) {
    super(message);
    this.message = message;
  }
}
