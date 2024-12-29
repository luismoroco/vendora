package com.vendora.engine.modules.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryImage {
  Long categoryImageId;
  Long categoryId;
  String url;
  Integer number;
}
