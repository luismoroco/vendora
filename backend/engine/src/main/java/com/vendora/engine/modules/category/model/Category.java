package com.vendora.engine.modules.category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
  Long categoryId;
  String name;
  Boolean featured;

  Set<CategoryImage> images;
}
