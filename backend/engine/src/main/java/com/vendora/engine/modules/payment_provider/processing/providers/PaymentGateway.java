package com.vendora.engine.modules.payment_provider.processing.providers;

import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.payment.model.Payment;

import java.util.Map;

public interface PaymentGateway {
  Payment initializeCheckout(Order order, Map<String, Object> args);

  Payment completeCheckout(Payment payment, Map<String, Object> args);

  void checkConfiguration();
}
