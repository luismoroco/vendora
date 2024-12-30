package com.vendora.engine.modules.order.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.order.model.OrderStatusType;
import com.vendora.engine.modules.order.request.UpdateOrderRequest;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRestRequest implements RequestAdapter<UpdateOrderRequest> {
  @Nullable
  private OrderStatusType orderStatusType;

  @Override
  public Class<UpdateOrderRequest> getTargetClass() {
    return UpdateOrderRequest.class;
  }
}
