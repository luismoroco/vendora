package com.vendora.engine.modules.category.dao;

import com.vendora.engine.modules.category.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryDao {
  boolean existCategoryByName(String name);

  Category saveCategory(Category category);

  int getCategoriesCount();

  Page<Category> getCategories(
    String name,
    Boolean featured,
    List<Long> categoryIds,
    Integer page,
    Integer size
  );

  Optional<Category> getCategoryById(Long categoryId);

  boolean existCategoryById(Long categoryId);

  void deleteCategoriesById(List<Long> categoryIds);

  Set<Category> getCategoriesById(List<Long> categoryIds);
}
