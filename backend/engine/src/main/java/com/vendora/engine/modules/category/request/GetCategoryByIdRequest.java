package com.vendora.engine.modules.category.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryByIdRequest {
  private Long categoryId;
}
