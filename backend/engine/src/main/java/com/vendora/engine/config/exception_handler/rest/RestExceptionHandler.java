package com.vendora.engine.config.exception_handler.rest;

import com.vendora.engine.common.error.model.ApiError;
import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.common.error.exc.exception.UnauthorizedException;
import com.vendora.engine.common.session.exc.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ApiError> notFoundException(NotFoundException exc) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(exc));
  }

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<ApiError> badRequestException(BadRequestException exc) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(exc));
  }

  @ExceptionHandler({UnauthorizedException.class})
  public ResponseEntity<ApiError> unauthorizedException(UnauthorizedException exc) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(exc));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exc) {
    var errors = new HashMap<String, String>();
    exc.getBindingResult().getAllErrors().forEach(error -> {
      var fieldName = ((FieldError) error).getField();
      var errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler({InvalidJwtException.class})
  public ResponseEntity<Object> invalidJwtException(InvalidJwtException exc) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(exc));
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(RuntimeException exc) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(exc));
  }
}
