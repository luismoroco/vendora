package com.vendora.engine.modules.shopping_cart.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.shopping_cart.request.UpdateShoppingCartRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShoppingCartRestRequest implements RequestAdapter<UpdateShoppingCartRequest> {
  @NotNull
  private List<ShoppingCartItemRestRequest> items;

  @Override
  public Class<UpdateShoppingCartRequest> getTargetClass() {
    return UpdateShoppingCartRequest.class;
  }
}
