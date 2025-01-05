package com.vendora.engine.common.event;

public interface EventSolver<E extends Event> {
  void execute(E event);
}
