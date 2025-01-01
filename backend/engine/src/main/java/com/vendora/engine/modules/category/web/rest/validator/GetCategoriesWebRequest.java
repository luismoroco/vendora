package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.GetCategoriesRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesWebRequest implements RequestAdapter<GetCategoriesRequest> {
  @Nullable
  private String name;

  @Nullable
  private Boolean featured;

  @Nullable
  private List<Long> categoryIds;

  @NotNull
  @Positive
  private Integer page;

  @NotNull
  @Positive
  private Integer size;

  @Override
  public Class<GetCategoriesRequest> getTargetClass() {
    return GetCategoriesRequest.class;
  }
}
