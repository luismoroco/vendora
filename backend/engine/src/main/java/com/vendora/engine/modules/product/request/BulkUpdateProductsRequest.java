package com.vendora.engine.modules.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BulkUpdateProductsRequest {
  private List<Long> productIds;
  private Integer stock;
}
