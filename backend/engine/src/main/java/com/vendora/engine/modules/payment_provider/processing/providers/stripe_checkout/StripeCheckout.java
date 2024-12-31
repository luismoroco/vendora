package com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout;

import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentMethodType;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import com.vendora.engine.modules.payment_provider.processing.providers.PaymentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("stripe_checkout")
public class StripeCheckout implements PaymentGateway {
  @Value("${application.payment-providers.stripe-checkout.public-key}")
  private String PUBLIC_KEY;
  @Value("${application.payment-providers.stripe-checkout.secret-key}")
  private String SECRET_KEY;
  @Value("${application.payment-providers.stripe-checkout.url}")
  private String URL;

  private Map<String, String> buildHeaders() {
    return Map.of(
      "Authorization", "Bearer %s".formatted(this.SECRET_KEY)
    );
  }

  @Override
  public Payment initializeCheckout(Order order, Map<String, Object> args) {
    var payment = order.initializePayment();
    payment.setPaymentMethodType(PaymentMethodType.CARD);
    payment.setPaymentProvider(PaymentProvider.STRIPE_CHECKOUT);

    return payment;
  }

  @Override
  public void completeCheckout(Payment payment) {

  }

  @Override
  public void checkConfiguration() {
    System.out.println("Hola desde el proveedor de stripe xd");
  }
}
