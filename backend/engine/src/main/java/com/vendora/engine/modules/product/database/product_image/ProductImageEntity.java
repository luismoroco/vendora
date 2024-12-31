package com.vendora.engine.modules.product.database.product_image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.product.database.product.ProductEntity;
import com.vendora.engine.modules.product.model.ProductImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_image")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageEntity implements ModelAdapter<ProductImage> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productImageId;
  @NotBlank
  private String url;
  @NotNull
  private Integer number;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "productId")
  private ProductEntity product;

  @Override
  public ProductImage toModel() {
    return MAPPER.map(this, ProductImage.class);
  }
}
