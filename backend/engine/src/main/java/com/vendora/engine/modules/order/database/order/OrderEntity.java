package com.vendora.engine.modules.order.database.order;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.database.order_item.OrderItemEntity;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Model<Order> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id", nullable = false, updatable = false)
  private Long orderId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_status_type", nullable = false)
  private OrderStatusType orderStatusType;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency", nullable = false)
  private Currency currency;

  @Positive
  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(
    cascade = {
      CascadeType.ALL
    },
    orphanRemoval = true)
  @JoinColumn(name = "order_id")
  private Set<OrderItemEntity> items;
}
