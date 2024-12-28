package com.vendora.engine.modules.order.database.order_item;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.order.model.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItemEntity extends Model<OrderItem> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id", nullable = false, updatable = false)
  private Long orderItemId;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "product_id", nullable = false)
  private Long productId;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @Positive
  @Column(name = "unit_price", nullable = false)
  private Double unitPrice;

  @Positive
  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
