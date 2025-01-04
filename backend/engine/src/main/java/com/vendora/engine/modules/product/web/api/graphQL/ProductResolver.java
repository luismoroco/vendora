package com.vendora.engine.modules.product.web.api.graphQL;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.product.ProductUseCase;
import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.product.request.DeleteProductByIdRequest;
import com.vendora.engine.modules.product.request.LikeProductRequest;
import com.vendora.engine.modules.product.web.validator.CreateProductWebRequest;
import com.vendora.engine.modules.product.web.validator.UpdateProductWebRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ProductResolver {
  private final ProductUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;

  public ProductResolver(
    ProductUseCase useCase,
    Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @MutationMapping
  @PreAuthorize("hasRole('MANAGER')")
  public Product createProduct(@Argument final CreateProductWebRequest input) {
    return this.useCase.createProduct(input.buildRequest());
  }

  @MutationMapping
  @PreAuthorize("hasRole('MANAGER')")
  public Product updateProduct(@Argument final UpdateProductWebRequest input, @Argument final Long productId) {
    return this.useCase.updateProduct(input.buildRequest(
      Map.of("productId", productId)
    ));
  }

  @MutationMapping
  @PreAuthorize("hasRole('MANAGER')")
  public Boolean deleteProductById(@Argument final Long productId) {
    var request = new DeleteProductByIdRequest(productId);

    this.useCase.deleteProductById(request);
    return Boolean.TRUE;
  }

  @MutationMapping
  @PreAuthorize("hasRole('CLIENT')")
  public Boolean putLikeProduct(@Argument final Long productId) {
    this.scrooge.setContext();

    var request = new LikeProductRequest(this.scrooge.retrieveKeys().getUserId(), productId);
    this.useCase.putLikeProduct(request);

    return Boolean.TRUE;
  }
}
