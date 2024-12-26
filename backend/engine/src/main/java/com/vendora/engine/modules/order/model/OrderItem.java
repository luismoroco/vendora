package com.vendora.engine.modules.order.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  Long orderItemId;
  Long orderId;
  Long productId;
  String name;
  Double unitPrice;
  Integer quantity;
}
