package com.vendora.engine.modules.category.request;

import lombok.*;

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
