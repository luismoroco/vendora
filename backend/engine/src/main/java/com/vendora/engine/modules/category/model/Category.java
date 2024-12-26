package com.vendora.engine.modules.category.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class Category {
  Long categoryId;
  String name;
  Boolean featured;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

  Set<CategoryImage> images;
}
