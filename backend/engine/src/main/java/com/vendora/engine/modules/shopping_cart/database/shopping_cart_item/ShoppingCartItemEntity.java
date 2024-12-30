package com.vendora.engine.modules.shopping_cart.database.shopping_cart_item;

import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shopping_cart_item")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemEntity implements ModelAdapter<ShoppingCartItem> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long shoppingCartItemId;
  @NotNull Long shoppingCartId;
  @NotNull Long productId;
   Integer quantity;
   Integer number;

  @Override
  public ShoppingCartItem toModel() {
    return MAPPER.map(this, ShoppingCartItem.class);
  }
}
