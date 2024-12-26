package com.vendora.engine.modules.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItem {
  Long shoppingCartItemId;
  Long shoppingCartId;
  Long productId;
  Integer quantity;
  Integer number;
}
