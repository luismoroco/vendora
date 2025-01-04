package com.vendora.engine.modules.order.event.order_event;

import com.vendora.engine.common.event.Event;
import com.vendora.engine.modules.order.event.OrderEventType;
import com.vendora.engine.modules.order.model.OrderStatusType;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent extends Event {
  private Long orderId;
  private Long userId;
  private OrderEventType orderEventType;
  private OrderStatusType orderStatusType;
}
