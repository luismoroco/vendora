package com.vendora.engine.modules.shopping_cart.event.shopping_cart_event;

import com.vendora.engine.common.event.Event;
import com.vendora.engine.modules.shopping_cart.event.ShoppingCartEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartEvent extends Event {
  private Long shoppingCartId;
  private Long userId;
  private ShoppingCartEventType shoppingCartEventType;
}
