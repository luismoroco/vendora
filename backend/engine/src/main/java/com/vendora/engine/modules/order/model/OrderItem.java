package com.vendora.engine.modules.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  @JsonIgnore
  private Long orderItemId;
  private Long productId;
  private String name;
  private Double unitPrice;
  private Integer quantity;

  @JsonIgnore
  private Order order;
}
