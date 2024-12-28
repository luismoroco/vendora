package com.vendora.engine.common.request;

import com.vendora.engine.common.mapper.Mapper;

import java.util.Map;

public class Request<T> implements Mapper<T> {
  @Override
  public T map() {
    return MAPPER.map(this, this.getTargetClass());
  }

  @Override
  public T map(Map<String, Object> overrideKeys) {
    T result = this.map();

    overrideKeys.forEach((fieldName, value) -> {
      try {
        var field = result.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(result, value);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException("Error setting field value: " + fieldName, e);
      }
    });

    return result;
  }
}
