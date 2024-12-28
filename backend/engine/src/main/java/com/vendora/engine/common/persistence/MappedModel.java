package com.vendora.engine.common.persistence;

import org.modelmapper.ModelMapper;

public interface MappedModel<T> {
  ModelMapper MAPPER = new ModelMapper();

  T toModel();
}
