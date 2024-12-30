package com.vendora.engine.modules.order.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.request.CreateOrderRequest;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRestRequest implements RequestAdapter<CreateOrderRequest> {
  @NotNull
  private PaymentProvider paymentProvider;
  @NotNull
  private Currency currency;

  @Override
  public Class<CreateOrderRequest> getTargetClass() {
    return CreateOrderRequest.class;
  }
}
