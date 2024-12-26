package com.vendora.engine.common.model;

import com.vendora.engine.common.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;

public interface Model<T> extends Mapper {
  Logger LOGGER = LoggerFactory.getLogger(Model.class);

  default T toModel() {
    return MAPPER.map(this, this.getTargetClass());
  }

  @SuppressWarnings("unchecked")
  default Class<T> getTargetClass() {
    var superclass = getClass().getGenericSuperclass();
    if (superclass instanceof ParameterizedType parameterized) {
      return (Class<T>) parameterized.getActualTypeArguments()[0];
    }

    LOGGER.error("Unexpected generic type [className=%s]".formatted(getClass().getName()));
    throw new IllegalStateException();
  }
}