package com.vendora.engine.modules.payment.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.payment.request.InitializeStripePaymentRequest;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitializeStripePaymentRestRequest implements RequestAdapter<InitializeStripePaymentRequest> {
  @NotNull
  @URL
  private String redirectUrl;

  @NotNull
  private Long orderId;

  @NotNull
  private PaymentProvider paymentProvider;

  @Override
  public Class<InitializeStripePaymentRequest> getTargetClass() {
    return InitializeStripePaymentRequest.class;
  }
}
