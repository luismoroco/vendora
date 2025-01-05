package com.vendora.engine.modules.order.event.order_event.consumer.providers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.order.event.order_event.OrderEvent;
import com.vendora.engine.modules.order.event.order_event.consumer.OrderEventConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Qualifier("kafka")
public class KafkaOrderEventConsumer implements OrderEventConsumer {
  private final Map<String, EventSolver<OrderEvent>> strategies;

  public KafkaOrderEventConsumer(
    Map<String, EventSolver<OrderEvent>> strategies
  ) {
    this.strategies = strategies;
  }

  @Override
  @KafkaListener(topics = "ORDERS", groupId = "1")
  public void consume(OrderEvent event) {
    var strategy = this.strategies.get(event.getOrderEventType().name().toLowerCase());
    strategy.execute(event);
  }
}
