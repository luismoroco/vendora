package com.vendora.engine.common.exc.exception;

import com.vendora.engine.common.exc.VendoraException;

public class VendoraNotFoundException extends VendoraException {
  public VendoraNotFoundException(String message) {
    super(message);
  }
}
