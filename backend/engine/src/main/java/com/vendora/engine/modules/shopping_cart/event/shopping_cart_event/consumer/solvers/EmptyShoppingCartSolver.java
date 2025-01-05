package com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.consumer.solvers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.shopping_cart.ShoppingCartUseCase;
import com.vendora.engine.modules.shopping_cart.event.shopping_cart_event.ShoppingCartEvent;
import com.vendora.engine.modules.shopping_cart.request.UpdateShoppingCartRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("empty_shopping_cart")
public class EmptyShoppingCartSolver implements EventSolver<ShoppingCartEvent> {
  private final ShoppingCartUseCase shoppingCartUseCase;

  public EmptyShoppingCartSolver(ShoppingCartUseCase shoppingCartUseCase) {
    this.shoppingCartUseCase = shoppingCartUseCase;
  }

  @Override
  public void execute(ShoppingCartEvent event) {
    var request = new UpdateShoppingCartRequest();
    request.setUserId(event.getUserId());
    request.setItems(List.of());

    this.shoppingCartUseCase.updateShoppingCart(request);
  }
}
