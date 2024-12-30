package com.vendora.engine.modules.shopping_cart.web.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.shopping_cart.ShoppingCartUseCase;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;
import com.vendora.engine.modules.shopping_cart.request.GetShoppingCartRequest;
import com.vendora.engine.modules.shopping_cart.web.rest.validator.UpdateShoppingCartRestRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCartController {
  private final ShoppingCartUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;

  public ShoppingCartController(
    ShoppingCartUseCase useCase,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @PutMapping("")
  @PreAuthorize("hasRole('CLIENT')")
  public ResponseEntity<ShoppingCart> updateShoppingCart(
    @Valid @RequestBody final UpdateShoppingCartRestRequest payload
  ) {
    this.scrooge.setContext();

    var shoppingCart = this.useCase.updateShoppingCart(payload.buildRequest(
      Map.of("userId", this.scrooge.retrieveKeys().getUserId())
    ));

    return ResponseEntity.status(HttpStatus.OK).body(shoppingCart);
  }

  @GetMapping("")
  @PreAuthorize("hasRole('CLIENT')")
  public ResponseEntity<ShoppingCart> getShoppingCart() {
    this.scrooge.setContext();

    var request = new GetShoppingCartRequest(this.scrooge.retrieveKeys().getUserId());
    var shoppingCart = this.useCase.getShoppingCart(request);

    return ResponseEntity.status(HttpStatus.OK).body(shoppingCart);
  }
}
