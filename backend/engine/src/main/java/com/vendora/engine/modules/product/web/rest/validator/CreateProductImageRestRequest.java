package com.vendora.engine.modules.product.web.rest.validator;

import com.vendora.engine.common.request.RequestAdapter;
import com.vendora.engine.modules.product.request.CreateProductImageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductImageRestRequest implements RequestAdapter<CreateProductImageRequest> {
  @URL
  private String url;

  @Override
  public Class<CreateProductImageRequest> getTargetClass() {
    return CreateProductImageRequest.class;
  }
}
