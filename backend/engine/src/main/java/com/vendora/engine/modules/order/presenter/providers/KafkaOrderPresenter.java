package com.vendora.engine.modules.order.presenter.providers;

import com.vendora.engine.modules.order.presenter.OrderPresenter;
import com.vendora.engine.modules.shopping_cart.event.ShoppingCartEventType;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.ShoppingCartEvent;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.emitter.ShoppingCartEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaOrderPresenter implements OrderPresenter {
  private final ShoppingCartEventEmitter shoppingCartEventEmitter;

  public KafkaOrderPresenter(@Qualifier("kafka") ShoppingCartEventEmitter shoppingCartEventEmitter) {
    this.shoppingCartEventEmitter = shoppingCartEventEmitter;
  }

  @Override
  public void notifyOrderCreated(Long orderId, Long userId) {
    this.shoppingCartEventEmitter.emit(
      ShoppingCartEvent.builder()
        .shoppingCartEventType(ShoppingCartEventType.EMPTY_SHOPPING_CART)
        .userId(userId)
        .build()
    );
  }
}
