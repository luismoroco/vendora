package com.vendora.engine.common.model;

import com.vendora.engine.common.mapper.Mapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

public class Model<T> implements Mapper<T> {
  @Override
  public T map() {
    return MAPPER.map(this, this.getTargetClass());
  }

  @Override
  public T map(Map<String, Object> overrideKeys) {
    throw new NotImplementedException("Operation not supported");
  }
}