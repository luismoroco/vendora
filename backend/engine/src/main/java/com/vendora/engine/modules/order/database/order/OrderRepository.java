package com.vendora.engine.modules.order.database.order;

import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.model.OrderStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  @Query(
    "SELECT o FROM OrderEntity o " +
      "WHERE (:orderStatusType IS NULL OR o.orderStatusType = :orderStatusType)" +
      "AND (:userIds IS NULL OR o.userId IN :userIds)" +
      "AND (:currency IS NULL OR o.currency = :currency)")
  Page<OrderEntity> getOrders(
    @Param("orderStatusType") OrderStatusType orderStatusType,
    @Param("userIds") List<Long> userIds,
    @Param("currency") Currency currency,
    final Pageable pageable
  );
}
