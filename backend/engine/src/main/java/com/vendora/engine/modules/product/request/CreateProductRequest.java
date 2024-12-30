package com.vendora.engine.modules.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
  private String name;
  private String description;
  private Double price;
  private Boolean enabled;
  private Boolean featured;
  private Integer stock;
  private List<Long> categoryIds;
  private List<CreateProductImageRequest> images;
}
