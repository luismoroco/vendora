package com.vendora.engine.modules.order;

import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.order.dao.OrderDao;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderStatusType;
import com.vendora.engine.modules.order.request.CreateOrderRequest;
import com.vendora.engine.modules.order.request.GetOrdersRequest;
import com.vendora.engine.modules.order.request.UpdateOrderRequest;
import com.vendora.engine.modules.order.sub_modules.OrderBuilder;
import com.vendora.engine.modules.product.dao.ProductDao;
import com.vendora.engine.modules.shopping_cart.dao.ShoppingCartDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrderUseCase.class);
  private final OrderDao dao;
  private final ProductDao productDao;
  private final ShoppingCartDao shoppingCartDao;

  public OrderUseCase(
    @Qualifier("postgresql") OrderDao dao,
    @Qualifier("postgresql") ProductDao productDao,
    @Qualifier("postgresql") ShoppingCartDao shoppingCartDao
  ) {
    this.dao = dao;
    this.productDao = productDao;
    this.shoppingCartDao = shoppingCartDao;
  }

  public Order createOrder(final CreateOrderRequest request) {
    var order = new OrderBuilder(this.shoppingCartDao, this.productDao)
      .builder()
      .addUser(request.getUserId())
      .addStatus(OrderStatusType.PENDING)
      .addCurrency(request.getCurrency())
      .addItems()
      .build();

    return this.dao.saverOrder(order);
  }

  public Page<Order> getOrders(final GetOrdersRequest request) {
    return this.dao.getOrders(
      request.getOrderStatusType(),
      request.getUserIds(),
      request.getCurrency(),
      request.getPage(),
      request.getSize()
    );
  }

  public Order updateOrder(final UpdateOrderRequest request) {
    var order = this.dao.getOrderById(request.getOrderId())
      .orElseThrow(
        () -> new NotFoundException("Order not found")
      );

    if (Objects.nonNull(request.getOrderStatusType())) {
      order.setOrderStatusType(request.getOrderStatusType());
    }

    return this.dao.saverOrder(order);
  }
}
