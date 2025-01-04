package com.vendora.engine.common.event;

public interface EventConsumer<E> {
  void consume(E event);
}
