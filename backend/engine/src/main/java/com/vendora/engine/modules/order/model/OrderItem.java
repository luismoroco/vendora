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
  @JsonIgnore Long orderItemId;
  Long productId;
  String name;
  Double unitPrice;
  Integer quantity;

  @JsonIgnore Order order;
}
