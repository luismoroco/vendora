package com.vendora.engine.modules.order.request;

import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.model.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersRequest {
  private OrderStatusType orderStatusType;
  private List<Long> userIds;
  private Currency currency;
  private Integer page;
  private Integer size;
}
