package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.GetCategoriesRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesRestRequest implements RequestAdapter<GetCategoriesRequest> {
  @Nullable
  String name;
  @Nullable
  Boolean featured;
  @Nullable
  List<Long> categoryIds;
  @NotNull Integer page;
  @NotNull Integer size;

  @Override
  public Class<GetCategoriesRequest> getTargetClass() {
    return GetCategoriesRequest.class;
  }
}
