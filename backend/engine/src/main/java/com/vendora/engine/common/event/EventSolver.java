package com.vendora.engine.common.event;

import com.vendora.engine.common.event.Event;

public interface EventSolver<E extends Event> {
  void execute(E event);
}
