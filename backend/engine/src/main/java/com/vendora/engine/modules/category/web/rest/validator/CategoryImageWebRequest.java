package com.vendora.engine.modules.category.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.category.request.CategoryImageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryImageWebRequest implements RequestAdapter<CategoryImageRequest> {
  @URL
  private String url;

  @Override
  public Class<CategoryImageRequest> getTargetClass() {
    return CategoryImageRequest.class;
  }
}
