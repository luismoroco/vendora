package com.vendora.engine.common.error.exc.exception;

import com.vendora.engine.common.error.exc.BaseException;

public class UnauthorizedException extends BaseException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
