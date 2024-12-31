package com.vendora.engine.modules.category.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesRequest {
  private String name;
  private Boolean featured;
  private List<Long> categoryIds;
  private Integer page;
  private Integer size;
}
