package com.vendora.engine.common.exc.exception;

import com.vendora.engine.common.exc.VendoraException;

public class NotFoundException extends VendoraException {
  public NotFoundException(String message) {
    super(message);
  }
}
