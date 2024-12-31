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
  @JsonIgnore
  private Map<String, Object> initializationData;
  @JsonIgnore
  private Map<String, Object> transactionData;
  @JsonIgnore
  private LocalDateTime paidAt;
  @JsonIgnore
  private Order order;

  @JsonIgnore
  public boolean isPaid() {
    return this.paymentStatusType == PaymentStatusType.PAID;
  }

  @JsonIgnore
  public boolean isPending() {
    return this.paymentStatusType == PaymentStatusType.PENDING;
  }
}
