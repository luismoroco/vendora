package com.vendora.engine.modules.order.presenter.providers;

import com.vendora.engine.modules.order.event.OrderEventType;
import com.vendora.engine.modules.order.event.order_event.OrderEvent;
import com.vendora.engine.modules.order.event.order_event.emitter.OrderEventEmitter;
import com.vendora.engine.modules.order.presenter.OrderPresenter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaOrderPresenter implements OrderPresenter {
  private final OrderEventEmitter emitter;

  public KafkaOrderPresenter(
    @Qualifier("kafka") OrderEventEmitter emitter
  ) {
    this.emitter = emitter;
  }

  @Override
  public void notifyOrderCreated(Long orderId, Long userId) {
    var event = OrderEvent.builder()
      .orderId(orderId)
      .userId(userId)
      .orderEventType(OrderEventType.ORDER_CREATED)
      .build();

    this.emitter.emit(event);
  }
}
