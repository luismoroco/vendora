package com.vendora.engine.modules.category.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
  Long categoryId;
  String name;
  Boolean featured;

  Set<CategoryImage> images;
}
