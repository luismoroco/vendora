package com.vendora.engine.modules.order.model;

import com.vendora.engine.modules.currency.model.Currency;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  Long orderId;
  Long userId;
  OrderStatusType orderStatusType;
  Currency currency;
  Double amount;

  Set<OrderItem> items;
}
