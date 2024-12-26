package com.vendora.engine.modules.product.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class Product {
  Long productId;
  String name;
  String description;
  Double price;
  Boolean enabled;
  Boolean archived;
  Boolean featured;
  Integer stock;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

  Set<ProductImage> images;
}
