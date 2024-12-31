package com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout.request;

import com.vendora.engine.modules.payment_provider.processing.providers.PaymentInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripePaymentInformation extends PaymentInformation {
  private Map<String, Object> data;
  private String redirectUrl;
}
