package com.vendora.engine.modules.product.database.product;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.product.database.product_image.ProductImageEntity;
import com.vendora.engine.modules.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity implements MappedModel<Product> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long productId;
  @NotBlank private String name;
  @NotBlank private String description;
  @Positive private Double price;
  @NotNull private Boolean enabled = Boolean.TRUE;
  @NotNull private Boolean archived = Boolean.FALSE;
  @NotNull private Boolean featured = Boolean.FALSE;
  @Positive private Integer stock;

  @Column(insertable = false) private LocalDateTime createdAt;
  @Column(insertable = false) private LocalDateTime updatedAt;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "productId")
  private Set<ProductImageEntity> images;

  @Override
  public Product toModel() {
    return MAPPER.map(this, Product.class);
  }
}
