package com.vendora.engine.modules.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private Long orderId;
  @JsonIgnore
  private Long userId;
  private OrderStatusType orderStatusType;
  private Currency currency;
  private Double amount;

  private Set<OrderItem> items;
  @JsonIgnore
  private Set<Payment> payments;

  @JsonIgnore
  public boolean isPaid() {
    return this.orderStatusType == OrderStatusType.PAID;
  }

  @JsonIgnore
  public boolean isCanceled() {
    return this.orderStatusType == OrderStatusType.CANCELED;
  }

  @JsonIgnore
  public void paid() {
    this.orderStatusType = OrderStatusType.PAID;
  }

  public Payment getPayment() {
    return this.payments.stream()
      .filter(Payment::isPaid)
      .findFirst()
      .orElse(null);
  }

  @JsonIgnore
  public Payment getPendingPayment() {
    return this.payments.stream()
      .filter(Payment::isPending)
      .findFirst()
      .orElse(null);
  }

  public Payment initializePayment() {
    if (this.isPaid()) {
      throw new RuntimeException("Order already paid");
    }

    var payment = new Payment();
    payment.setPaymentStatusType(PaymentStatusType.PENDING);
    payment.setOrder(this);

    this.payments.add(payment);

    return payment;
  }

  public void expirePayments() {
    this.getPayments().stream()
      .filter(Payment::isPending)
      .forEach(payment -> payment.setPaymentStatusType(PaymentStatusType.CANCELED));
  }
}
