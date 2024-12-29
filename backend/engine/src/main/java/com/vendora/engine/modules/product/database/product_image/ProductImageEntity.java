package com.vendora.engine.modules.product.database.product_image;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.product.model.ProductImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_image")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageEntity implements MappedModel<ProductImage> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productImageId;
  @NotNull
  private Long productId;
  @NotBlank
  private String url;
  @Positive
  private Integer number;

  @Override
  public ProductImage toModel() {
    return MAPPER.map(this, ProductImage.class);
  }
}
