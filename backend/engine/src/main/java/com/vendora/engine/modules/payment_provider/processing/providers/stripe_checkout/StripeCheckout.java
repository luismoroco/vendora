package com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentMethodType;
import com.vendora.engine.modules.payment.model.PaymentStatusType;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import com.vendora.engine.modules.payment_provider.processing.providers.PaymentGateway;
import com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout.request.StripePaymentInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component("stripe_checkout")
public class StripeCheckout implements PaymentGateway {
  Logger LOGGER = LoggerFactory.getLogger(StripeCheckout.class);
  @Value("${application.payment-providers.stripe-checkout.public-key}")
  private String PUBLIC_KEY;
  @Value("${application.payment-providers.stripe-checkout.secret-key}")
  private String SECRET_KEY;
  @Value("${application.payment-providers.stripe-checkout.url}")
  private String URL;

  private static Long fixAmount(Double amount) {
    Double fixedAmount = amount * 100;
    return fixedAmount.longValue();
  }

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
      payment.setInitializationData(Map.of(
        "url", session.getUrl(),
        "id", session.getId(),
        "metadata", session.getMetadata()
      ));

      return payment;
    } catch (StripeException e) {
      LOGGER.error("Error in initialization payment [paymentProvider=%s][orderId=%d][error=%s]"
        .formatted(PaymentProvider.STRIPE_CHECKOUT.name(), order.getOrderId(), e));

      throw new RuntimeException("Error in payment");
    }
  }

  @Override
  public Payment completeCheckout(Payment payment, Map<String, Object> args) {
    var paymentInformation = (StripePaymentInformation) args.get("args");
    var object = (Map<String, Object>) paymentInformation.getData().get("object");
    var paymentStatus = object.get("payment_status");

    if ("paid".equals(paymentStatus)) {
      payment.setPaymentStatusType(PaymentStatusType.PAID);
      payment.setTransactionData(paymentInformation.getData());
      payment.setPaidAt(LocalDateTime.now());
      payment.getOrder().paid();

      return payment;
    }

    throw new BadRequestException("Invalid paid");
  }

  @Override
  public void checkConfiguration() {
    Stripe.apiKey = this.SECRET_KEY;

    try {
      Account.retrieve();
    } catch (StripeException e) {
      LOGGER.error("Invalid Keys [paymentProvider=%s][error=%s]"
        .formatted(PaymentProvider.STRIPE_CHECKOUT.name(), e));

      throw new RuntimeException("Invalid keys");
    }
  }
}
