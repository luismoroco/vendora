package com.vendora.engine.common.error.exc.exception;

import com.vendora.engine.common.error.exc.BaseException;

public class BadRequestException extends BaseException {
  public BadRequestException(String message) {
    super(message);
  }
}
