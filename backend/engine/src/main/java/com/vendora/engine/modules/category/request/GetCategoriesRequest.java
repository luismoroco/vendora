package com.vendora.engine.modules.category.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesRequest {
  private String name;
  private Boolean featured;
  private List<Long> categoryIds;
  private Integer page;
  private Integer size;
}
