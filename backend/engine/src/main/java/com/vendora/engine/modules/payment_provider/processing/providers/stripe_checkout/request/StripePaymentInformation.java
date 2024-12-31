package com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout.request;

import com.vendora.engine.modules.payment_provider.processing.providers.PaymentInformation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripePaymentInformation extends PaymentInformation {
  private String redirectUrl;
}
