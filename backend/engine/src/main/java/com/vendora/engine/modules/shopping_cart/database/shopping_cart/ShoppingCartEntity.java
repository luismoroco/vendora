package com.vendora.engine.modules.shopping_cart.database.shopping_cart;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.shopping_cart.database.shopping_cart_item.ShoppingCartItemEntity;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartEntity implements MappedModel<ShoppingCart> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long shoppingCartId;
  @NotNull private Long userId;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "shoppingCartId")
  private Set<ShoppingCartItemEntity> items;

  @Override
  public ShoppingCart toModel() {
    return MAPPER.map(this, ShoppingCart.class);
  }
}
