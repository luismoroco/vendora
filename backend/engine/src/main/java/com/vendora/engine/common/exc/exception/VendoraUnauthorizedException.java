package com.vendora.engine.common.exc.exception;

import com.vendora.engine.common.exc.VendoraException;

public class VendoraUnauthorizedException extends VendoraException {
  public VendoraUnauthorizedException(String message) {
    super(message);
  }
}
