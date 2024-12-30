package com.vendora.engine.modules.order.database;

import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.dao.OrderDao;
import com.vendora.engine.modules.order.database.order.OrderEntity;
import com.vendora.engine.modules.order.database.order.OrderRepository;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderStatusType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("postgresql")
public class OrderDaoPg implements OrderDao {
  private final OrderRepository repository;
  private final ModelMapper mapper;

  public OrderDaoPg(
    OrderRepository repository,
    ModelMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Order saverOrder(Order order) {
    var orderEntity = mapper.map(order, OrderEntity.class);
    return this.repository.save(orderEntity).toModel();
  }

  @Override
  public Page<Order> getOrders(
    OrderStatusType orderStatusType,
    List<Long> userIds,
    Currency currency,
    Integer page,
    Integer size
  ) {
    return this.repository.getOrders(
      orderStatusType,
      userIds,
      currency,
      PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"))
    ).map(OrderEntity::toModel);
  }

  @Override
  public Optional<Order> getOrderById(Long orderId) {
    return this.repository.findById(orderId).map(OrderEntity::toModel);
  }
}
