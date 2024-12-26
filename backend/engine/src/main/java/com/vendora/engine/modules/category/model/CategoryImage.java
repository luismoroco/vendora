package com.vendora.engine.modules.category.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryImage {
  Long categoryImageId;
  Long categoryId;
  String url;
  Integer number;
}
