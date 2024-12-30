package com.vendora.engine.modules.category.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
  private Long categoryId;
  private String name;
  private Boolean featured;
  private List<CategoryImageRequest> images;
}
