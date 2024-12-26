package com.vendora.engine.modules.category.database.category_image;

import com.vendora.engine.common.model.Model;
import com.vendora.engine.modules.category.model.CategoryImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "category_image")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryImageEntity implements Model<CategoryImage> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_image_id", nullable = false, updatable = false)
  private Long categoryImageId;

  @Column(name = "category_id", nullable = false)
  private Long categoryId;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "number", nullable = false)
  private Integer number;
}
