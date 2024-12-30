package com.vendora.engine.modules.product.web.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.product.ProductUseCase;
import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.product.request.DeleteProductByIdRequest;
import com.vendora.engine.modules.product.request.LikeProductRequest;
import com.vendora.engine.modules.product.web.rest.validator.CreateProductRestRequest;
import com.vendora.engine.modules.product.web.rest.validator.GetProductsRestRequest;
import com.vendora.engine.modules.product.web.rest.validator.UpdateProductRestRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;

  public ProductController(
    ProductUseCase useCase,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @PostMapping("")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Product> createProduct(
    @Valid @RequestBody final CreateProductRestRequest payload
  ) {
    var product = this.useCase.createProduct(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.CREATED).body(product);
  }

  @GetMapping("")
  public ResponseEntity<Page<Product>> getProducts(@Valid final GetProductsRestRequest payload) {
    var products = this.useCase.getProducts(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.OK).body(products);
  }

  @PutMapping("/{productId}")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Product> updateProduct(
    @Valid @RequestBody final UpdateProductRestRequest payload,
    @PathVariable final Long productId
  ) {
    var product = this.useCase.updateProduct(payload.buildRequest(
      Map.of("productId", productId)
    ));

    return ResponseEntity.status(HttpStatus.OK).body(product);
  }

  @DeleteMapping("/{productId}")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Void> deleteProductById(@PathVariable final Long productId) {
    var request = new DeleteProductByIdRequest(productId);
    this.useCase.deleteProductById(request);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PutMapping("/{productId}/likes")
  @PreAuthorize("hasRole('CLIENT')")
  public ResponseEntity<Void> putLikeProduct(@PathVariable final Long productId) {
    this.scrooge.setContext();

    var request = new LikeProductRequest(this.scrooge.retrieveKeys().getUserId(), productId);
    this.useCase.putLikeProduct(request);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
