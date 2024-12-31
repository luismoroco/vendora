package com.vendora.engine.modules.shopping_cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItem {
  @JsonIgnore
  Long shoppingCartItemId;
  Long productId;
  Integer quantity;
  Integer number;

  @JsonIgnore
  ShoppingCart shoppingCart;
}
