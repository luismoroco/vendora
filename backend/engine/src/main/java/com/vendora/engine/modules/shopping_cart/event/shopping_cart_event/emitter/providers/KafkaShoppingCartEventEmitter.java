package com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.emitter.providers;

import com.vendora.engine.kafka.model.KafkaTopic;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.ShoppingCartEvent;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.emitter.ShoppingCartEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaShoppingCartEventEmitter implements ShoppingCartEventEmitter {
  private static final String TOPIC = KafkaTopic.SHOPPING_CART.name();
  private final KafkaTemplate<String, ShoppingCartEvent> kafkaTemplate;

  public KafkaShoppingCartEventEmitter(KafkaTemplate<String, ShoppingCartEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void emit(ShoppingCartEvent event) {
    this.kafkaTemplate.send(TOPIC, event);
  }
}
