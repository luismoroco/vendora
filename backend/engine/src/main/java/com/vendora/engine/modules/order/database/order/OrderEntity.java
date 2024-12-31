package com.vendora.engine.modules.order.database.order;

import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.database.order_item.OrderItemEntity;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderStatusType;
import com.vendora.engine.modules.payment.database.payment.PaymentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements ModelAdapter<Order> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  @NotNull
  private Long userId;
  @Enumerated(EnumType.STRING)
  private OrderStatusType orderStatusType;
  @Enumerated(EnumType.STRING)
  private Currency currency;
  @NotNull
  @PositiveOrZero
  private Double amount;

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt;
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<OrderItemEntity> items;

  @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<PaymentEntity> payments;

  @Override
  public Order toModel() {
    return MAPPER.map(this, Order.class);
  }
}
