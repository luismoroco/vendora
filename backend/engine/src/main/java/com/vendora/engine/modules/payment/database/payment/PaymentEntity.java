package com.vendora.engine.modules.payment.database.payment;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.model.PaymentMethodType;
import com.vendora.engine.modules.payment.model.PaymentStatusType;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class PaymentEntity implements MappedModel<Payment> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long paymentId;
  @NotNull private Long orderId;
  @Enumerated(EnumType.STRING) private PaymentMethodType paymentMethodType;
  @Enumerated(EnumType.STRING) private PaymentProvider paymentProvider;
  @Enumerated(EnumType.STRING) private PaymentStatusType paymentStatusType;
  @JdbcTypeCode(SqlTypes.JSON) private Map<String, Object> initializationData;
  @JdbcTypeCode(SqlTypes.JSON) private Map<String, Object> transactionData;
  private LocalDateTime paidAt;

  @Column(insertable = false) private LocalDateTime createdAt;
  @Column(insertable = false) private LocalDateTime updatedAt;

  @Override
  public Payment toModel() {
    return MAPPER.map(this, Payment.class);
  }
}
