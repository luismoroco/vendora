package com.vendora.engine.modules.category;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.modules.category.dao.CategoryDao;
import com.vendora.engine.modules.category.model.Category;
import com.vendora.engine.modules.category.request.CreateCategoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;

@Component
public class CategoryUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryUseCase.class);
  private final CategoryDao dao;
  @Value("${application.constraints.category.max-images}")
  private Integer MAX_IMAGES_PER_CATEGORY;

  public CategoryUseCase(
    @Qualifier("postgresql") CategoryDao dao
  ) {
    this.dao = dao;
  }

  public Category createCategory(final CreateCategoryRequest request) {
    this.validateCategoryConstraints(request.getImages().size(), request.getName());

    var category = new Category();
    category.setName(request.getName());
    category.setFeatured(request.getFeatured());
    category.setImages(new HashSet<>());

    return this.dao.saveCategory(category);
  }

  private void validateCategoryConstraints(Integer imagesLength, String categoryName) {
    if (Objects.nonNull(imagesLength)) {
      if (imagesLength > MAX_IMAGES_PER_CATEGORY) {
        throw new BadRequestException(
          "Category images exceeded [imagesLength=%d][maxAllowed=%d]"
            .formatted(imagesLength, MAX_IMAGES_PER_CATEGORY)
        );
      }
    }

    if (Objects.nonNull(categoryName)) {
      if (this.dao.existCategoryByName(categoryName)) {
        throw new BadRequestException("Category already exists");
      }
    }
  }
}
