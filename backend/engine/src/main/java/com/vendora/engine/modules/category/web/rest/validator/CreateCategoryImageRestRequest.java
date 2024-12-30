package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.CreateCategoryImageRequest;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryImageRestRequest implements RequestAdapter<CreateCategoryImageRequest> {
  @URL
  private String url;

  @Override
  public Class<CreateCategoryImageRequest> getTargetClass() {
    return CreateCategoryImageRequest.class;
  }
}
