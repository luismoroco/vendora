package com.vendora.engine.modules.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
  @JsonIgnore
  Long productImageId;
  String url;
  Integer number;

  @JsonIgnore
  Product product;
}
