package com.vendora.engine.modules.payment.web.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.payment.PaymentUseCase;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.web.rest.validator.InitializePaymentRestRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
  private final PaymentUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;

  public PaymentController(
    PaymentUseCase useCase,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @PostMapping("/stripe/initialize")
  @PreAuthorize("hasRole('CLIENT')")
  public ResponseEntity<Payment> initializeStripeCheckout(
    @Valid @RequestBody final InitializePaymentRestRequest payload
  ) {
    this.scrooge.setContext();

    var payment = this.useCase.initializeStripeCheckout(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.OK).body(payment);
  }
}
