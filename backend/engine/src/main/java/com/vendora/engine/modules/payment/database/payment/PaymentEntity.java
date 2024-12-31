package com.vendora.engine.modules.payment.database.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.order.database.order.OrderEntity;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentMethodType;
import com.vendora.engine.modules.payment.model.PaymentStatusType;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity implements ModelAdapter<Payment> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentId;
  @Enumerated(EnumType.STRING)
  private PaymentMethodType paymentMethodType;
  @Enumerated(EnumType.STRING)
  private PaymentProvider paymentProvider;
  @Enumerated(EnumType.STRING)
  private PaymentStatusType paymentStatusType;
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> initializationData;
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> transactionData;
  private LocalDateTime paidAt;

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt;
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderId")
  private OrderEntity order;

  @Override
  public Payment toModel() {
    return MAPPER.map(this, Payment.class);
  }
}
