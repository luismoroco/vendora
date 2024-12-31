package com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentMethodType;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import com.vendora.engine.modules.payment_provider.processing.providers.PaymentGateway;
import com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout.request.StripePaymentInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  Logger LOGGER = LoggerFactory.getLogger(StripeCheckout.class);

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

    if (!args.containsKey("args")) {
      throw new BadRequestException("Required parameters not provided");
    }

    var paymentInformation = (StripePaymentInformation) args.get("args");

    Stripe.apiKey = this.SECRET_KEY;
    var sessionParams = SessionCreateParams.builder()
      .setSuccessUrl(paymentInformation.getRedirectUrl())
      .setCancelUrl(paymentInformation.getRedirectUrl())
      .putMetadata("externalReference", order.getOrderId().toString())
      .addLineItem(
        SessionCreateParams.LineItem.builder()
          .setName("Order %d".formatted(order.getOrderId()))
          .setAmount(fixAmount(order.getAmount()))
          .setCurrency(order.getCurrency().name())
          .setQuantity(1L)
          .build()
      )
      .setMode(SessionCreateParams.Mode.PAYMENT)
      .build();

    try {
      var session = Session.create(sessionParams);
      return payment;
    } catch (StripeException e) {
      LOGGER.error("Error in stripe initialization payment [orderId=%d][error=%s]".formatted(order.getOrderId(), e));
      throw new RuntimeException("Error in payment");
    }
  }

  @Override
  public void completeCheckout(Payment payment) {

  }

  @Override
  public void checkConfiguration() {
  }

  private static Long fixAmount(Double amount) {
    Double fixedAmount = amount * 100;
    return fixedAmount.longValue();
  }
}
