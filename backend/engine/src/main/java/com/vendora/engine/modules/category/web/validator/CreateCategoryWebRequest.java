package com.vendora.engine.modules.category.web.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.CreateCategoryRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryWebRequest implements RequestAdapter<CreateCategoryRequest> {
  @NotBlank
  private String name;

  @NotNull
  private Boolean featured;

  @NotNull
  private List<CategoryImageWebRequest> images;

  @Override
  public Class<CreateCategoryRequest> getTargetClass() {
    return CreateCategoryRequest.class;
  }
}
