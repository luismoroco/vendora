package com.vendora.engine.modules.order.request;

import com.vendora.engine.modules.order.model.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
  private Long orderId;
  private OrderStatusType orderStatusType;
}
