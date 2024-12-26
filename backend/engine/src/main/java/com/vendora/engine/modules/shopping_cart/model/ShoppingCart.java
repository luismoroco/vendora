package com.vendora.engine.modules.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
  Long shoppingCartId;
  Long productId;

  Set<ShoppingCartItem> items;
}
