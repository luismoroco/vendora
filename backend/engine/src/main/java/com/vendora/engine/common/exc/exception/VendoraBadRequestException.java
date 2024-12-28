package com.vendora.engine.common.exc.exception;

import com.vendora.engine.common.exc.VendoraException;

public class VendoraBadRequestException extends VendoraException {
  public VendoraBadRequestException(String message) {
    super(message);
  }
}
