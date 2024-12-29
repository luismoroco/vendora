package com.vendora.engine.modules.category.database.category_image;

import com.vendora.engine.common.persistence.MappedModel;
import com.vendora.engine.modules.category.model.CategoryImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "category_image")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryImageEntity implements MappedModel<CategoryImage> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryImageId;
  @NotNull
  private Long categoryId;
  @NotNull
  private String url;
  @NotNull
  private Integer number;

  @Override
  public CategoryImage toModel() {
    return MAPPER.map(this, CategoryImage.class);
  }
}
