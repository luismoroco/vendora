package com.vendora.engine.modules.order.event.order_event.consumer;

import com.vendora.engine.common.event.EventConsumer;
import com.vendora.engine.modules.order.event.order_event.OrderEvent;

public interface OrderEventConsumer extends EventConsumer<OrderEvent> {
}
