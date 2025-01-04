package com.vendora.engine.modules.order.event.order_event.emitter.providers;

import com.vendora.engine.kafka.model.KafkaTopic;
import com.vendora.engine.modules.order.event.order_event.OrderEvent;
import com.vendora.engine.modules.order.event.order_event.emitter.OrderEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaOrderEventEmitter implements OrderEventEmitter {
  private static final String TOPIC = KafkaTopic.ORDERS.name();
  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  public KafkaOrderEventEmitter(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void emit(OrderEvent event) {
    this.kafkaTemplate.send(TOPIC, event);
  }
}
