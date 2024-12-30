package com.vendora.engine.modules.category.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
  private String name;
  private Boolean featured;
  private List<CategoryImageRequest> images;
}
