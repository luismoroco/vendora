package com.vendora.engine.common.session.exc;

import com.vendora.engine.common.error.exc.BaseException;

public class InvalidJwtException extends BaseException {
  public InvalidJwtException(String message) {
    super(message);
  }
}
