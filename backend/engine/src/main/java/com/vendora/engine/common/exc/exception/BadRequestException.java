package com.vendora.engine.common.exc.exception;

import com.vendora.engine.common.exc.VendoraException;

public class BadRequestException extends VendoraException {
  public BadRequestException(String message) {
    super(message);
  }
}
