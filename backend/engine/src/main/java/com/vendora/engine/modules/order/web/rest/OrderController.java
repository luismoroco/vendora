package com.vendora.engine.modules.order.web.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.order.OrderUseCase;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.web.rest.validator.CreateOrderRestRequest;
import com.vendora.engine.modules.order.web.rest.validator.GetOrdersRestRequest;
import com.vendora.engine.modules.order.web.rest.validator.UpdateOrderRestRequest;
import com.vendora.engine.modules.user.model.UserType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
  private final OrderUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;

  public OrderController(
    OrderUseCase useCase,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @PostMapping("")
  @PreAuthorize("hasRole('CLIENT')")
  public ResponseEntity<Order> createOrder(
    @Valid @RequestBody final CreateOrderRestRequest payload
  ) {
    this.scrooge.setContext();

    var order = this.useCase.createOrder(payload.buildRequest(
      Map.of("userId", this.scrooge.retrieveKeys().getUserId())
    ));

    return ResponseEntity.status(HttpStatus.OK).body(order);
  }

  @GetMapping("")
  @PreAuthorize("hasAnyRole('CLIENT', 'MANAGER')")
  public ResponseEntity<Page<Order>> getOrders(@Valid final GetOrdersRestRequest payload) {
    this.scrooge.setContext();

    var request = payload.buildRequest();
    if (this.scrooge.retrieveKeys().getUserType() == UserType.CLIENT) {
      request.setUserIds(List.of(this.scrooge.retrieveKeys().getUserId()));
    }

    var orders = this.useCase.getOrders(request);

    return ResponseEntity.status(HttpStatus.OK).body(orders);
  }

  @PutMapping("/{orderId}")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Order> updateOrder(
    @Valid @RequestBody final UpdateOrderRestRequest payload,
    @PathVariable final Long orderId
  ) {
    var order = this.useCase.updateOrder(payload.buildRequest(
      Map.of("orderId", orderId)
    ));

    return ResponseEntity.status(HttpStatus.OK).body(order);
  }
}
