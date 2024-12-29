package com.vendora.engine.modules.category.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
  private String name;
  private Boolean featured;
  private List<CreateCategoryImageRequest> images;
}
