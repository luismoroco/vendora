package com.vendora.engine.modules.order.request;

import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
  private Long userId;
  private PaymentProvider paymentProvider;
  private Currency currency;
}
