package com.vendora.engine.common.event;

public interface EventEmitter<E> {
  void emit(E event);
}
