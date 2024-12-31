package com.vendora.engine.modules.shopping_cart.database.shopping_cart;

import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.shopping_cart.database.shopping_cart_item.ShoppingCartItemEntity;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartEntity implements ModelAdapter<ShoppingCart> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long shoppingCartId;
  @NotNull
  private Long userId;

  @OneToMany(mappedBy = "shoppingCart", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<ShoppingCartItemEntity> items;

  @Override
  public ShoppingCart toModel() {
    return MAPPER.map(this, ShoppingCart.class);
  }
}
