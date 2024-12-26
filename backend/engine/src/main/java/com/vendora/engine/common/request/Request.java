package com.vendora.engine.common.request;

import com.vendora.engine.common.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public interface Request extends Mapper {
  Logger LOGGER = LoggerFactory.getLogger(Request.class);

  static <K extends Request, T> K builtFrom(T source, Class<K> targetClass) {
    return MAPPER.map(source, targetClass);
  }

  static <K extends Request, T> K builtFrom(T source, Class<K> targetClass, Map<String, Object> overrideKeyValues) {
    K target = builtFrom(source, targetClass);

    overrideKeyValues.forEach((key, value) -> {
      try {
        var field = target.getClass().getDeclaredField(key);
        field.setAccessible(true);
        field.set(target, value);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        LOGGER.error("Error while setting field value [key=%s][error=%s]".formatted(key, e));
        throw new RuntimeException(e);
      }
    });

    return target;
  }
}
