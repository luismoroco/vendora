package com.vendora.engine.modules.product.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
  Long productImageId;
  Long productId;
  String url;
  Integer number;
}
