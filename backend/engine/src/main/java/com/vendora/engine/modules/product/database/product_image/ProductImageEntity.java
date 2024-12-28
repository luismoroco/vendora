package com.vendora.engine.modules.product.database.product_image;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.product.model.ProductImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_image")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductImageEntity extends Model<ProductImage> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_image_id", nullable = false, updatable = false)
  private Long productImageId;

  @NotBlank
  @Column(name = "product_id", nullable = false)
  private Long productId;

  @NotBlank
  @Column(name = "url", nullable = false)
  private String url;

  @Positive
  @Column(name = "number", nullable = false)
  private Integer number;
}
