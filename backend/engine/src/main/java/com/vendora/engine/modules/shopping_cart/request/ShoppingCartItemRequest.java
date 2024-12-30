package com.vendora.engine.modules.shopping_cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemRequest {
  private Long productId;
  private Integer quantity;
}