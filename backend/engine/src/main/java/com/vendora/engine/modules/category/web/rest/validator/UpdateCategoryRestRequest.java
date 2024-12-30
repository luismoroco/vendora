package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.UpdateCategoryRequest;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRestRequest implements RequestAdapter<UpdateCategoryRequest> {
  @Nullable
  private String name;

  @Nullable
  private Boolean featured;

  @Nullable
  private List<CategoryImageRestRequest> images;

  @Override
  public Class<UpdateCategoryRequest> getTargetClass() {
    return UpdateCategoryRequest.class;
  }
}
