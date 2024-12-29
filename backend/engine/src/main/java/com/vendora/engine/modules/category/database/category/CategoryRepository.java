package com.vendora.engine.modules.category.database.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  boolean existsByName(String name);

  @Query("SELECT COUNT(*) FROM CategoryEntity")
  int getCategoriesCount();
}
