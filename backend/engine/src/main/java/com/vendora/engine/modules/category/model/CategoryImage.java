package com.vendora.engine.modules.category.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryImage {
  @JsonIgnore
  Long categoryImageId;
  String url;
  Integer number;

  @JsonIgnore
  Category category;
}
