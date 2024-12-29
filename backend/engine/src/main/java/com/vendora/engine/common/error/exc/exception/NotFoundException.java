package com.vendora.engine.common.error.exc.exception;

import com.vendora.engine.common.error.exc.BaseException;

public class NotFoundException extends BaseException {
  public NotFoundException(String message) {
    super(message);
  }
}
