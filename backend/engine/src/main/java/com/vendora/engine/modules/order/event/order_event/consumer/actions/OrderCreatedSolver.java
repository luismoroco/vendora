package com.vendora.engine.modules.order.event.order_event.consumer.actions;

import com.vendora.engine.modules.order.event.order_event.OrderEvent;
import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.shopping_cart.ShoppingCartUseCase;
import com.vendora.engine.modules.shopping_cart.request.UpdateShoppingCartRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("order_created")
public class OrderCreatedSolver implements EventSolver<OrderEvent> {
  private final ShoppingCartUseCase shoppingCartUseCase;

  public OrderCreatedSolver(
    ShoppingCartUseCase shoppingCartUseCase
  ) {
    this.shoppingCartUseCase = shoppingCartUseCase;
  }

  @Override
  public void execute(OrderEvent event) {
    var cleanShoppingCartRequest = new UpdateShoppingCartRequest();
    cleanShoppingCartRequest.setUserId(event.getUserId());
    cleanShoppingCartRequest.setItems(List.of());
    this.shoppingCartUseCase.updateShoppingCart(cleanShoppingCartRequest);
  }
}
