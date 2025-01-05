package com.vendora.engine.modules.product.event.product_event.emitter.providers;

import com.vendora.engine.kafka.model.KafkaTopic;
import com.vendora.engine.modules.product.event.product_event.ProductEvent;
import com.vendora.engine.modules.product.event.product_event.emitter.ProductEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaProductEventEmitter implements ProductEventEmitter {
  private static final String TOPIC = KafkaTopic.PRODUCT.name();
  private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

  public KafkaProductEventEmitter(KafkaTemplate<String, ProductEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void emit(ProductEvent event) {
    this.kafkaTemplate.send(TOPIC, event);
  }
}
