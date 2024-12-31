package com.vendora.engine.modules.payment.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.payment.request.InitializePaymentRequest;
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
public class InitializePaymentRestRequest implements RequestAdapter<InitializePaymentRequest> {
  @NotNull @URL
  private String redirectUrl;

  @NotNull
  private Long orderId;

  @NotNull
  private PaymentProvider paymentProvider;

  @Override
  public Class<InitializePaymentRequest> getTargetClass() {
    return InitializePaymentRequest.class;
  }
}
