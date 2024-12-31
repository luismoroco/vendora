package com.vendora.engine.modules.shopping_cart.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.shopping_cart.request.ShoppingCartItemRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemRestRequest implements RequestAdapter<ShoppingCartItemRequest> {
  @NotNull
  private Long productId;

  @NotNull
  @Positive
  private Integer quantity;

  @Override
  public Class<ShoppingCartItemRequest> getTargetClass() {
    return ShoppingCartItemRequest.class;
  }
}
