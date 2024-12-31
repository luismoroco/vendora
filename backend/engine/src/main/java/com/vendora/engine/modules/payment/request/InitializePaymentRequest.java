package com.vendora.engine.modules.payment.request;

import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitializePaymentRequest {
  private String redirectUrl;
  private Long orderId;
  private PaymentProvider paymentProvider;
}
