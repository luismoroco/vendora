package com.vendora.engine.modules.order.database.order_item;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.order.model.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity implements MappedModel<OrderItem> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long orderItemId;
  @NotNull private Long orderId;
  @NotNull private Long productId;
  @NotBlank private String name;
  @Positive private Double unitPrice;
  @Positive private Integer quantity;

  @Override
  public OrderItem toModel() {
    return MAPPER.map(this, OrderItem.class);
  }
}
