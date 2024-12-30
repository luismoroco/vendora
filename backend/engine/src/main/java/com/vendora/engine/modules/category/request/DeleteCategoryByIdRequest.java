package com.vendora.engine.modules.category.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCategoryByIdRequest {
  private Long categoryId;
}
