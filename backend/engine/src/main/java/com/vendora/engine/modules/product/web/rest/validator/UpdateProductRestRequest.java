package com.vendora.engine.modules.product.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.product.request.UpdateProductRequest;
import jakarta.annotation.Nullable;
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
public class UpdateProductRestRequest implements RequestAdapter<UpdateProductRequest> {
  @Nullable
  private String name;

  @Nullable
  private String description;

  @Nullable @PositiveOrZero
  private Double price;

  @Nullable
  private Boolean enabled;

  @Nullable
  private Boolean featured;

  @Nullable
  private Boolean archived;

  @Nullable @PositiveOrZero
  private Integer stock;

  @Nullable
  private List<CreateProductImageRestRequest> images;

  @Override
  public Class<UpdateProductRequest> getTargetClass() {
    return UpdateProductRequest.class;
  }
}
