package com.vendora.engine.modules.payment.database;

import com.vendora.engine.modules.payment.dao.PaymentDao;
import com.vendora.engine.modules.payment.database.payment.PaymentEntity;
import com.vendora.engine.modules.payment.database.payment.PaymentRepository;
import com.vendora.engine.modules.payment.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("postgresql")
public class PaymentDaoPg implements PaymentDao {
  private final PaymentRepository repository;
  private final ModelMapper mapper;

  public PaymentDaoPg(
    PaymentRepository repository,
    ModelMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Payment savePayment(Payment payment) {
    var paymentEntity = mapper.map(payment, PaymentEntity.class);
    return this.repository.save(paymentEntity).toModel();
  }

  @Override
  public Optional<Payment> getPaymentById(Long paymentId) {
    return this.repository.findById(paymentId).map(PaymentEntity::toModel);
  }
}
