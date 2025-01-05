package com.vendora.engine.modules.product.event.product_event;

import com.vendora.engine.common.event.Event;
import com.vendora.engine.modules.product.event.ProductEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent extends Event {
  private Long productId;
  private Long orderId;
  private ProductEventType productEventType;
}
