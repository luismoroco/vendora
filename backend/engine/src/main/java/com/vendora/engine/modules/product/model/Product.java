package com.vendora.engine.modules.product.model;

import com.vendora.engine.modules.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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

  Set<ProductImage> images;
  Set<Category> categories;
}
