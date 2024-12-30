package com.vendora.engine.modules.shopping_cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShoppingCartRequest {
  private Long userId;
  private List<ShoppingCartItemRequest> items;
}
