package com.vendora.engine.modules.payment.dao;

import com.vendora.engine.modules.payment.model.Payment;

import java.util.Optional;

public interface PaymentDao {
  Payment savePayment(Payment payment);

  Optional<Payment> getPaymentById(Long paymentId);
}
