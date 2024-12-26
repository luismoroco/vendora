package com.vendora.engine.common.exc.exception;

import com.vendora.engine.common.exc.VendoraException;

public class UnauthorizedException extends VendoraException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
