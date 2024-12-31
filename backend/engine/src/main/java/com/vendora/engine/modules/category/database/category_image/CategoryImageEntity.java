package com.vendora.engine.modules.category.database.category_image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vendora.engine.common.persistence.ModelAdapter;
import com.vendora.engine.modules.category.database.category.CategoryEntity;
import com.vendora.engine.modules.category.model.CategoryImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category_image")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryImageEntity implements ModelAdapter<CategoryImage> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryImageId;
  @NotNull
  private String url;
  @NotNull
  private Integer number;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoryId")
  private CategoryEntity category;

  @Override
  public CategoryImage toModel() {
    return MAPPER.map(this, CategoryImage.class);
  }
}
