package com.vendora.engine.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
  @JsonIgnore
  private Long paymentId;
  private PaymentMethodType paymentMethodType;
  private PaymentProvider paymentProvider;
  private PaymentStatusType paymentStatusType;
  private Map<String, Object> initializationData;
  private Map<String, Object> transactionData;
  private LocalDateTime paidAt;

  @JsonIgnore
  private Order order;

  public boolean isPending() {
    return this.paymentStatusType == PaymentStatusType.PENDING;
  }
}
