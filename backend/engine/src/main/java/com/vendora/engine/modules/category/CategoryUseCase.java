package com.vendora.engine.modules.category;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.category.dao.CategoryDao;
import com.vendora.engine.modules.category.model.Category;
import com.vendora.engine.modules.category.model.CategoryImage;
import com.vendora.engine.modules.category.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
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

  private static void addCategoryImages(Category category, List<CategoryImageRequest> imageRequests) {
    int imagesLength = imageRequests.size();
    var images = new HashSet<CategoryImage>(imagesLength);
    for (int index = 0; index < imagesLength; index++) {
      var imageRequest = imageRequests.get(index);

      var image = new CategoryImage();
      image.setUrl(imageRequest.getUrl());
      image.setNumber(index);
      image.setCategory(category);

      images.add(image);
    }

    category.setImages(images);
  }

  public Category createCategory(final CreateCategoryRequest request) {
    int imagesLength = request.getImages().size();
    this.validateCategoryConstraints(imagesLength, request.getName());

    var category = new Category();
    category.setName(request.getName());
    category.setFeatured(request.getFeatured());
    category.setImages(new HashSet<>());

    addCategoryImages(category, request.getImages());

    return this.dao.saveCategory(category);
  }

  public Category getCategoryById(final GetCategoryByIdRequest request) {
    return this.dao.getCategoryById(request.getCategoryId())
      .orElseThrow(
        () -> new NotFoundException("Category not found")
      );
  }

  public Category updateCategoryById(final UpdateCategoryRequest request) {
    var imagesLength = Objects.isNull(request.getImages()) ? null : request.getImages().size();
    this.validateCategoryConstraints(imagesLength, request.getName());

    var category = this.dao.getCategoryById(request.getCategoryId())
      .orElseThrow(
        () -> new NotFoundException("Category not found")
      );

    if (Objects.nonNull(request.getName())) {
      category.setName(request.getName());
    }

    if (Objects.nonNull(request.getFeatured())) {
      category.setFeatured(request.getFeatured());
    }

    if (Objects.nonNull(request.getImages())) {
      addCategoryImages(category, request.getImages());
    }

    return this.dao.saveCategory(category);
  }

  public Page<Category> getCategories(final GetCategoriesRequest request) {
    return this.dao.getCategories(
      request.getName(),
      request.getFeatured(),
      request.getCategoryIds(),
      request.getPage(),
      request.getSize()
    );
  }

  public void deleteCategoryById(final DeleteCategoryByIdRequest request) {
    if (!this.dao.existCategoryById(request.getCategoryId())) {
      throw new NotFoundException("Category not found");
    }

    this.dao.deleteCategoriesById(List.of(request.getCategoryId()));
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
