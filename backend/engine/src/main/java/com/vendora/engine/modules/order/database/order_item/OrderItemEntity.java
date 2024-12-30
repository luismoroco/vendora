package com.vendora.engine.modules.order.database.order_item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.order.database.order.OrderEntity;
import com.vendora.engine.modules.order.model.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity implements ModelAdapter<OrderItem> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;
  @NotNull
  private Long productId;
  @NotBlank
  private String name;
  @NotNull @PositiveOrZero
  private Double unitPrice;
  @NotNull @PositiveOrZero
  private Integer quantity;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderId")
  private OrderEntity order;

  @Override
  public OrderItem toModel() {
    return MAPPER.map(this, OrderItem.class);
  }
}
