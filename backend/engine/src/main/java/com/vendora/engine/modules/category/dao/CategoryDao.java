package com.vendora.engine.modules.category.dao;

import com.vendora.engine.modules.category.model.Category;

public interface CategoryDao {
  boolean existCategoryByName(String name);

  Category saveCategory(Category category);

  int getCategoriesCount();
}
