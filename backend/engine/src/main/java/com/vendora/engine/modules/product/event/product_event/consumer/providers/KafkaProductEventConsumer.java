package com.vendora.engine.modules.product.event.product_event.consumer.providers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.product.event.product_event.ProductEvent;
import com.vendora.engine.modules.product.event.product_event.consumer.ProductEventConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Qualifier("kafka")
public class KafkaProductEventConsumer implements ProductEventConsumer {
  private final Map<String, EventSolver<ProductEvent>> solverMap;

  public KafkaProductEventConsumer(Map<String, EventSolver<ProductEvent>> solverMap) {
    this.solverMap = solverMap;
  }

  @Override
  @KafkaListener(topics = "PRODUCT", groupId = "1")
  public void consume(ProductEvent event) {
    var solver = this.solverMap.get(event.getProductEventType().name().toLowerCase());
    solver.execute(event);
  }
}
