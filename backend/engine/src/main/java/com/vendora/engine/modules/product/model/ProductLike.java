package com.vendora.engine.modules.product.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductLike {
  Long productLikeId;
  Long productId;
  Long userId;
}
