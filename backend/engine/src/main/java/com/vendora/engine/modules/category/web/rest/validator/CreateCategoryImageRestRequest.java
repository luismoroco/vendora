package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.CreateCategoryImageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryImageRestRequest implements RequestAdapter<CreateCategoryImageRequest> {
  @URL String url;

  @Override
  public Class<CreateCategoryImageRequest> getTargetClass() {
    return CreateCategoryImageRequest.class;
  }
}
