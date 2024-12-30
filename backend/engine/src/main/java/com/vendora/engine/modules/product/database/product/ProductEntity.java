package com.vendora.engine.modules.product.database.product;

import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.category.database.category.CategoryEntity;
import com.vendora.engine.modules.product.database.product_image.ProductImageEntity;
import com.vendora.engine.modules.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity implements ModelAdapter<Product> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @PositiveOrZero
  private Double price;
  @NotNull
  private Boolean enabled = Boolean.TRUE;
  @NotNull
  private Boolean archived = Boolean.FALSE;
  @NotNull
  private Boolean featured = Boolean.FALSE;
  @PositiveOrZero
  private Integer stock;

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt;
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<ProductImageEntity> images;

  @ManyToMany(
    fetch = FetchType.LAZY,
    cascade = { CascadeType.MERGE, CascadeType.REFRESH }
  )
  @JoinTable(
    name = "category_product",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<CategoryEntity> categories;

  @Override
  public Product toModel() {
    return MAPPER.map(this, Product.class);
  }
}
