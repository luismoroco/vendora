package com.vendora.engine.modules.shopping_cart.database.shopping_cart_item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.shopping_cart.database.shopping_cart.ShoppingCartEntity;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart_item")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemEntity implements ModelAdapter<ShoppingCartItem> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long shoppingCartItemId;
  @NotNull
  private Long productId;
  @NotNull
  private Integer quantity;
  @NotNull
  private Integer number;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shoppingCartId")
  private ShoppingCartEntity shoppingCart;

  @Override
  public ShoppingCartItem toModel() {
    return MAPPER.map(this, ShoppingCartItem.class);
  }
}
