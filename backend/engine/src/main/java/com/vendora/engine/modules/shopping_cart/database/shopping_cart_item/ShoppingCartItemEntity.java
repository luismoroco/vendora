package com.vendora.engine.modules.shopping_cart.database.shopping_cart_item;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCartItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shopping_cart_item")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartItemEntity extends Model<ShoppingCartItem> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shopping_cart_item_id", nullable = false, updatable = false)
  Long shoppingCartItemId;

  @Column(name = "shopping_cart_id", nullable = false)
  Long shoppingCartId;

  @Column(name = "product_id", nullable = false)
  Long productId;

  @Positive
  @Column(name = "quantity", nullable = false)
  Integer quantity;

  @Positive
  @Column(name = "number", nullable = false)
  Integer number;
}
