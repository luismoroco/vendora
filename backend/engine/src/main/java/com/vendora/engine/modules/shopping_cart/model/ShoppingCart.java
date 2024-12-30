package com.vendora.engine.modules.shopping_cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
  @JsonIgnore Long shoppingCartId;
  @JsonIgnore Long userId;

  Set<ShoppingCartItem> items;
}
