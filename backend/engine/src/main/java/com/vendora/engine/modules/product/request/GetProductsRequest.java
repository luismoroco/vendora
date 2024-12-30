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
public class GetProductsRequest {
  private String name;
  private Boolean enabled;
  private Boolean featured;
  private List<Long> categoryIds;
  private Double minPrice;
  private Double maxPrice;
  private Integer page;
  private Integer size;
}
