package com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.consumer.providers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.ShoppingCartEvent;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.consumer.ShoppingCartEventConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Qualifier("kafka")
public class KafkaShoppingCartEventConsumer implements ShoppingCartEventConsumer {
  private final Map<String, EventSolver<ShoppingCartEvent>> solverMap;

  public KafkaShoppingCartEventConsumer(Map<String, EventSolver<ShoppingCartEvent>> solverMap) {
    this.solverMap = solverMap;
  }

  @Override
  @KafkaListener(topics = "SHOPPING_CART", groupId = "1")
  public void consume(ShoppingCartEvent event) {
    var solver = this.solverMap.get(event.getShoppingCartEventType().name().toLowerCase());
    solver.execute(event);
  }
}
