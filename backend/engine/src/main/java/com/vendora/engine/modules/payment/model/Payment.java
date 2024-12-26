package com.vendora.engine.modules.payment.model;

import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class Payment {
  Long paymentId;
  Long orderId;
  PaymentMethodType paymentMethodType;
  PaymentProvider paymentProvider;
  PaymentStatusType paymentStatusType;
  Map<String, Object> initializationData;
  Map<String, Object> transactionData;
  LocalDateTime paidAt;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
