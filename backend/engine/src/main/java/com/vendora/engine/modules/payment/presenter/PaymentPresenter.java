package com.vendora.engine.modules.payment.presenter;

public interface PaymentPresenter {
  void notifyPaymentPaid(Long paymentId, Long orderId);
}
