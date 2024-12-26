package com.vendora.engine.modules.payment.database.payment;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentMethodType;
import com.vendora.engine.modules.payment.model.PaymentStatusType;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity implements Model<Payment> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id", nullable = false, updatable = false)
  private Long paymentId;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method_type", nullable = false)
  private PaymentMethodType paymentMethodType;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_provider", nullable = false)
  private PaymentProvider paymentProvider;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status_type", nullable = false)
  private PaymentStatusType paymentStatusType;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "initialization_data")
  private Map<String, Object> initializationData;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "transaction_data")
  private Map<String, Object> transactionData;

  @Column(name = "paid_at", nullable = false)
  private LocalDateTime paidAt;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false)
  private LocalDateTime updatedAt;
}
