package com.vendora.engine.modules.payment_provider.processing;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import com.vendora.engine.modules.payment_provider.processing.providers.PaymentGateway;
import com.vendora.engine.modules.payment_provider.processing.providers.PaymentInformation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class PaymentProcessor {
  private final Map<String, PaymentGateway> paymentGateways;
  private PaymentGateway gateway;

  public PaymentProcessor(
    Map<String, PaymentGateway> paymentGateways
  ) {
    this.paymentGateways = paymentGateways;

    if (this.paymentGateways.size() == 0) {
      throw new RuntimeException("No payment providers configured");
    }
  }

  public void setGateway(PaymentProvider paymentProvider) {
    var gateway = this.paymentGateways.get(paymentProvider.name().toLowerCase());
    if (Objects.isNull(gateway)) {
      throw new IllegalArgumentException("Payment provider not found");
    }

    this.gateway = gateway;
  }

  public <P extends PaymentInformation> Payment initializeCheckout(Order order, P args) {
    this.validateOrder(order);
    order.expirePayments();

    return this.gateway.initializeCheckout(order, Map.of("args", args));
  }

  private void validateOrder(Order order) {
    if (order.isPaid() || order.isCanceled()) {
      throw new BadRequestException("Invalid invoice state");
    }
  }

  public void checkConfiguration() {
    this.gateway.checkConfiguration();
  }
}
