package com.vendora.engine.modules.product.database.product_like;

import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.product.model.ProductLike;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_like")
@NoArgsConstructor
@AllArgsConstructor
public class ProductLikeEntity implements ModelAdapter<ProductLike> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productLikeId;
  @NotNull
  private Long productId;
  @NotNull
  private Long userId;

  @Override
  public ProductLike toModel() {
    return MAPPER.map(this, ProductLike.class);
  }
}
