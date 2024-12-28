package com.vendora.engine.common.mapper;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

public interface Mapper<T> {
  ModelMapper MAPPER = new ModelMapper();

  T map();
  T map(Map<String, Object> overrideKeys);

  @SuppressWarnings("unchecked")
  default Class<T> getTargetClass() {
    var superclass = getClass().getGenericSuperclass();
    if (superclass instanceof ParameterizedType parameterized) {
      return (Class<T>) parameterized.getActualTypeArguments()[0];
    }

    throw new IllegalStateException();
  }
}
