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
public class UpdateProductRequest {
  private Long productId;
  private String name;
  private String description;
  private Double price;
  private Boolean enabled;
  private Boolean featured;
  private Boolean archived;
  private Integer stock;
  private List<CreateProductImageRequest> images;
}
