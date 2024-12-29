package com.vendora.engine.modules.order.database.order;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.database.order_item.OrderItemEntity;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class OrderEntity implements MappedModel<Order> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  @NotNull
  private Long userId;
  @Enumerated(EnumType.STRING)
  private OrderStatusType orderStatusType;
  @Enumerated(EnumType.STRING)
  private Currency currency;
  @Positive
  private Double amount;

  @Column(insertable = false)
  private LocalDateTime createdAt;
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "orderId")
  private Set<OrderItemEntity> items;

  @Override
  public Order toModel() {
    return MAPPER.map(this, Order.class);
  }
}
