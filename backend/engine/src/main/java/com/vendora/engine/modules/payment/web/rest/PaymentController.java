package com.vendora.engine.modules.payment.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.common.web.webhook.Webhook;
import com.vendora.engine.modules.payment.PaymentUseCase;
import com.vendora.engine.modules.payment.model.Payment;
import com.vendora.engine.modules.payment.request.CompleteStripePaymentRequest;
import com.vendora.engine.modules.payment.web.rest.validator.InitializeStripePaymentRestRequest;
import com.vendora.engine.modules.payment_provider.model.PaymentProvider;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
  private final PaymentUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;
  private final ObjectMapper objectMapper;

  public PaymentController(
    PaymentUseCase useCase,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge,
    ObjectMapper objectMapper
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
    this.objectMapper = objectMapper;
  }

  @PostMapping("/stripe/initialize")
  @PreAuthorize("hasRole('CLIENT')")
  public ResponseEntity<Payment> initializeStripeCheckout(
    @Valid @RequestBody final InitializeStripePaymentRestRequest payload
  ) {
    this.scrooge.setContext();

    var payment = this.useCase.initializeStripePayment(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.OK).body(payment);
  }

  @Webhook(origin = "stripe_checkout")
  @PostMapping("/stripe/complete")
  public ResponseEntity<Void> completeStripeCheckout(@RequestBody String payload) throws JsonProcessingException {
    var webhookPayload = objectMapper.readValue(payload, Map.class);

    Map<String, String> metadata;
    Map<String, Object> data;
    try {
      data = (Map<String, Object>) webhookPayload.get("data");
      var object = (Map<String, Object>) data.get("object");
      metadata = (Map<String, String>) object.get("metadata");
    } catch (IllegalArgumentException e) {
      LOGGER.error("Key not found in webhook [paymentProvider=%s][payload=%s][error=%s]"
        .formatted(PaymentProvider.STRIPE_CHECKOUT, payload, e));

      throw new RuntimeException(e);
    }

    var request = new CompleteStripePaymentRequest();
    request.setData(data);
    request.setOrderId(Long.parseLong(metadata.get("externalReference")));

    var payment = this.useCase.completeStripePayment(request);
    if (payment.isPaid()) {
      // logic
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
