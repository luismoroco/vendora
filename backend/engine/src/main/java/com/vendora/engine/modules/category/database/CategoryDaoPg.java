package com.vendora.engine.modules.category.database;

import com.vendora.engine.modules.category.dao.CategoryDao;
import com.vendora.engine.modules.category.database.category.CategoryEntity;
import com.vendora.engine.modules.category.database.category.CategoryRepository;
import com.vendora.engine.modules.category.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("postgresql")
public class CategoryDaoPg implements CategoryDao {
  private final CategoryRepository repository;
  private final ModelMapper mapper;

  public CategoryDaoPg(
    CategoryRepository repository,
    ModelMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public boolean existCategoryByName(String name) {
    return this.repository.existsByName(name);
  }

  @Override
  public Category saveCategory(Category category) {
    var categoryEntity = mapper.map(category, CategoryEntity.class);
    return this.repository.save(categoryEntity).toModel();
  }

  @Override
  public int getCategoriesCount() {
    return this.repository.getCategoriesCount();
  }
}
