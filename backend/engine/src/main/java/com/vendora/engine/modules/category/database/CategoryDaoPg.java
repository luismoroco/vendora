package com.vendora.engine.modules.category.database;

import com.vendora.engine.modules.category.dao.CategoryDao;
import com.vendora.engine.modules.category.database.category.CategoryEntity;
import com.vendora.engine.modules.category.database.category.CategoryRepository;
import com.vendora.engine.modules.category.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  @Override
  public Page<Category> getCategories(
    String name,
    Boolean featured,
    List<Long> categoryIds,
    Integer page,
    Integer size
  ) {
    return this.repository.getCategories(
      name == null ? "" : name,
      featured,
      categoryIds,
      PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"))
    ).map(CategoryEntity::toModel);
  }

  @Override
  public Optional<Category> getCategoryById(Long categoryId) {
    return this.repository.getCategoryEntityByCategoryId(categoryId).map(CategoryEntity::toModel);
  }

  @Override
  public boolean existCategoryById(Long categoryId) {
    return this.repository.existsByCategoryId(categoryId);
  }

  @Override
  public void deleteCategoriesById(List<Long> categoryIds) {
    this.repository.deleteAllById(categoryIds);
  }
}
