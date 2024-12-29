package com.vendora.engine.modules.category.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryImage {
  Long categoryImageId;
  Long categoryId;
  String url;
  Integer number;
}
