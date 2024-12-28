package com.vendora.engine.exception.rest;

import com.vendora.engine.common.api.model.ApiError;
import com.vendora.engine.common.exc.exception.VendoraBadRequestException;
import com.vendora.engine.common.exc.exception.VendoraNotFoundException;
import com.vendora.engine.common.exc.exception.VendoraUnauthorizedException;
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
  @ExceptionHandler({VendoraNotFoundException.class})
  public ResponseEntity<ApiError> vendoraNotFoundException(VendoraNotFoundException exc) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(exc));
  }

  @ExceptionHandler({VendoraBadRequestException.class})
  public ResponseEntity<ApiError> vendoraBadRequestException(VendoraBadRequestException exc) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(exc));
  }

  @ExceptionHandler({VendoraUnauthorizedException.class})
  public ResponseEntity<ApiError> vendoraUnauthorizedException(VendoraUnauthorizedException exc) {
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

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(RuntimeException exc) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(exc));
  }
}
