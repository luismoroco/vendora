package com.vendora.engine.modules.product.database.product;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.product.database.product_image.ProductImageEntity;
import com.vendora.engine.modules.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends Model<Product> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false, updatable = false)
  private Long productId;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @Positive
  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled = true;

  @Column(name = "archived", nullable = false)
  private Boolean archived = false;

  @Column(name = "featured", nullable = false)
  private Boolean featured = false;

  @Positive
  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(
    cascade = {
      CascadeType.ALL
    },
    orphanRemoval = true)
  @JoinColumn(name = "product_id")
  private Set<ProductImageEntity> images;
}
