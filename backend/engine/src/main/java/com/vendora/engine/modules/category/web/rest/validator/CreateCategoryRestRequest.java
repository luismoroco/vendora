package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.CreateCategoryRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRestRequest implements RequestAdapter<CreateCategoryRequest> {
  @NotBlank
  private String name;

  @NotNull
  private Boolean featured;

  @NotNull
  private List<CreateCategoryImageRestRequest> images;

  @Override
  public Class<CreateCategoryRequest> getTargetClass() {
    return CreateCategoryRequest.class;
  }
}
