package com.vendora.engine.modules.payment.presenter.providers;

import com.vendora.engine.modules.payment.presenter.PaymentPresenter;
import com.vendora.engine.modules.product.event.ProductEventType;
import com.vendora.engine.modules.product.event.product_event.ProductEvent;
import com.vendora.engine.modules.product.event.product_event.emitter.ProductEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaPaymentPresenter implements PaymentPresenter {
  private final ProductEventEmitter productEventEmitter;

  public KafkaPaymentPresenter(@Qualifier("kafka") ProductEventEmitter productEventEmitter) {
    this.productEventEmitter = productEventEmitter;
  }

  @Override
  public void notifyPaymentPaid(Long paymentId, Long orderId) {
    this.productEventEmitter.emit(
      ProductEvent.builder()
        .productEventType(ProductEventType.UPDATE_PRODUCTS_STOCK)
        .orderId(orderId)
        .build()
    );
  }
}
