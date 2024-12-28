package com.vendora.engine.common.api.model;

import com.vendora.engine.common.api.Response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ApiError implements Response {
  private String type;
  private String message;
  private Timestamp timestamp;

  public ApiError(RuntimeException exc) {
    this.type = exc.getClass().getSimpleName();
    this.message = exc.getMessage();
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
}
