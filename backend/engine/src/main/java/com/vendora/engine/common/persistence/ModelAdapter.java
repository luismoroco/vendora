package com.vendora.engine.common.persistence;

import org.modelmapper.ModelMapper;

public interface ModelAdapter<T> {
  ModelMapper MAPPER = new ModelMapper();

  T toModel();
}
