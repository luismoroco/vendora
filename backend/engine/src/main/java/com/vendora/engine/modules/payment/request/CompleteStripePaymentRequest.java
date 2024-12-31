package com.vendora.engine.modules.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteStripePaymentRequest {
  private Map<String, Object> data;
  private Long orderId;
}
