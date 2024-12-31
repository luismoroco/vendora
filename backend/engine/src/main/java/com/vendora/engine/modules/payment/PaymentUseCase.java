package com.vendora.engine.modules.payment;

import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.order.dao.OrderDao;
import com.vendora.engine.modules.payment.dao.PaymentDao;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.request.CompleteStripePaymentRequest;
import com.vendora.engine.modules.payment.request.GetPaymentByIdRequest;
import com.vendora.engine.modules.payment.request.InitializeStripePaymentRequest;
import com.vendora.engine.modules.payment_provider.processing.PaymentProcessor;
import com.vendora.engine.modules.payment_provider.processing.providers.stripe_checkout.request.StripePaymentInformation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentUseCase {
  private final OrderDao orderDao;
  private final PaymentDao dao;
  private final PaymentProcessor paymentProcessor;

  public PaymentUseCase(
    @Qualifier("postgresql") OrderDao orderDao,
    @Qualifier("postgresql") PaymentDao dao,
    PaymentProcessor paymentProcessor
  ) {
    this.orderDao = orderDao;
    this.dao = dao;
    this.paymentProcessor = paymentProcessor;
  }

  public Payment getPaymentById(final GetPaymentByIdRequest request) {
    return this.dao.getPaymentById(request.getPaymentId())
      .orElseThrow(
        () -> new NotFoundException("Payment not found")
      );
  }

  public Payment initializeStripePayment(final InitializeStripePaymentRequest request) {
    var order = this.orderDao.getOrderById(request.getOrderId())
      .orElseThrow(
        () -> new NotFoundException("Order not found")
      );

    var args = new StripePaymentInformation();
    args.setRedirectUrl(request.getRedirectUrl());

    this.paymentProcessor.setGateway(request.getPaymentProvider());
    var payment = this.paymentProcessor.initializeCheckout(order, args);

    this.orderDao.saverOrder(order);

    return payment;
  }

  public Payment completeStripePayment(final CompleteStripePaymentRequest request) {
    var order = this.orderDao.getOrderById(request.getOrderId())
      .orElseThrow(
        () -> new NotFoundException("Payment not found")
      );

    var payment = order.getPendingPayment();
    this.paymentProcessor.setGateway(payment.getPaymentProvider());

    var args = new StripePaymentInformation();
    args.setData(request.getData());

    payment = this.paymentProcessor.completePayment(payment, args);
    this.orderDao.saverOrder(order);

    return payment;
  }
}
