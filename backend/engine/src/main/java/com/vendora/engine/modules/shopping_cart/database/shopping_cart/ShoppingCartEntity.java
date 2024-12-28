package com.vendora.engine.modules.shopping_cart.database.shopping_cart;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.order.database.order_item.OrderItemEntity;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartEntity extends Model<ShoppingCart> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shopping_cart_id", nullable = false, updatable = false)
  Long shoppingCartId;

  @Column(name = "user_id", nullable = false, unique = true)
  private Long userId;

  @OneToMany(
    cascade = {
      CascadeType.ALL
    },
    orphanRemoval = true)
  @JoinColumn(name = "shopping_cart_id")
  private Set<OrderItemEntity> items;
}
