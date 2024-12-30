package com.vendora.engine.modules.order.dao;

import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderStatusType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
  Order saverOrder(Order order);

  Page<Order> getOrders(
    OrderStatusType orderStatusType,
    List<Long> userIds,
    Currency currency,
    Integer page,
    Integer size
  );

  Optional<Order> getOrderById(Long orderId);
}
