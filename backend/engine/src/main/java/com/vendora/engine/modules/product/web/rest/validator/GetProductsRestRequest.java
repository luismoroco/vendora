package com.vendora.engine.modules.product.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.product.request.GetProductsRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsRestRequest implements RequestAdapter<GetProductsRequest> {
  @Nullable
  private String name;

  @Nullable
  private Boolean enabled;

  @Nullable
  private Boolean featured;

  @Nullable
  private List<Long> categoryIds;

  @Nullable
  @PositiveOrZero
  private Double minPrice;

  @Nullable
  @PositiveOrZero
  private Double maxPrice;

  @NotNull
  private Integer page;

  @NotNull
  private Integer size;

  @Override
  public Class<GetProductsRequest> getTargetClass() {
    return GetProductsRequest.class;
  }
}
