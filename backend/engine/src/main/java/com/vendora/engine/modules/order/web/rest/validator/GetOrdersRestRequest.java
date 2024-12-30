package com.vendora.engine.modules.order.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.model.OrderStatusType;
import com.vendora.engine.modules.order.request.GetOrdersRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersRestRequest implements RequestAdapter<GetOrdersRequest> {
  @Nullable
  private OrderStatusType orderStatusType;

  @Nullable
  private List<Long> userIds;

  @Nullable
  private Currency currency;

  @NotNull @Positive
  private Integer page;

  @NotNull @Positive
  private Integer size;

  @Override
  public Class<GetOrdersRequest> getTargetClass() {
    return GetOrdersRequest.class;
  }
}
