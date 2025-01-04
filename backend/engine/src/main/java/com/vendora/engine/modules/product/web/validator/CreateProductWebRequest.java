package com.vendora.engine.modules.product.web.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.product.request.CreateProductRequest;
import jakarta.validation.constraints.NotBlank;
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
public class CreateProductWebRequest implements RequestAdapter<CreateProductRequest> {
  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  @PositiveOrZero
  private Double price;

  @NotNull
  private Boolean enabled;

  @NotNull
  private Boolean featured;

  @NotNull
  @PositiveOrZero
  private Integer stock;

  @NotNull
  private List<Long> categoryIds;

  @NotNull
  private List<CreateProductImageWebRequest> images;

  @Override
  public Class<CreateProductRequest> getTargetClass() {
    return CreateProductRequest.class;
  }
}
