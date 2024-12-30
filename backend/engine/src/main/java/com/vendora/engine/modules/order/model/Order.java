package com.vendora.engine.modules.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.modules.currency.model.Currency;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private Long orderId;
  @JsonIgnore private Long userId;
  private OrderStatusType orderStatusType;
  private Currency currency;
  private Double amount;

  private Set<OrderItem> items;
}
