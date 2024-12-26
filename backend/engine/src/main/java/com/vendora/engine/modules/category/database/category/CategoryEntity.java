package com.vendora.engine.modules.category.database.category;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.category.database.category_image.CategoryImageEntity;
import com.vendora.engine.modules.category.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity implements Model<Category> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id", nullable = false, updatable = false)
  private Long categoryId;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "featured", nullable = false)
  private Boolean featured = false;

  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(
    cascade = {
      CascadeType.ALL
    },
    orphanRemoval = true)
  @JoinColumn(name = "category_id")
  private Set<CategoryImageEntity> images;
}
