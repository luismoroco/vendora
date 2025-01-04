package com.vendora.engine.modules.order.presenter;

public interface OrderPresenter {
  void notifyOrderCreated(Long orderId, Long userId);
}
