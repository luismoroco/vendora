package com.vendora.engine.modules.order.model;

import com.vendora.engine.modules.currency.model.Currency;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class Order {
  Long orderId;
  Long userId;
  OrderStatusType orderStatusType;
  Currency currency;
  Double amount;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

  Set<OrderItem> items;
}
