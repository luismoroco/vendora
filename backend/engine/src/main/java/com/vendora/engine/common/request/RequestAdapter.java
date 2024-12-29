package com.vendora.engine.common.request;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public interface RequestAdapter<T> {
  ModelMapper MAPPER = new ModelMapper();
  Logger LOGGER = LoggerFactory.getLogger(RequestAdapter.class);

  default T buildRequest() {
    return MAPPER.map(this, this.getTargetClass());
  }

  default T buildRequest(Map<String, Object> overrideKeys) {
    T result = this.buildRequest();

    overrideKeys.forEach((key, value) -> {
      try {
        var field = result.getClass().getDeclaredField(key);
        field.setAccessible(true);
        field.set(result, value);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        LOGGER.error("Error while overriding field [fieldName=%s]".formatted(key));
        throw new RuntimeException(e);
      }
    });

    return result;
  }

  Class<T> getTargetClass();
}
