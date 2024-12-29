package com.vendora.engine.modules.category.database.category;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.category.database.category_image.CategoryImageEntity;
import com.vendora.engine.modules.category.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CategoryEntity implements MappedModel<Category> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;
  @NotBlank
  private String name;
  @NotNull
  private Boolean featured = Boolean.FALSE;

  @Column(insertable = false)
  private LocalDateTime createdAt;
  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "categoryId")
  private Set<CategoryImageEntity> images;

  @Override
  public Category toModel() {
    return MAPPER.map(this, Category.class);
  }
}
