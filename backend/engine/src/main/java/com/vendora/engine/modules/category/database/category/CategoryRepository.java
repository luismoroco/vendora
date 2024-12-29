package com.vendora.engine.modules.category.database.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  boolean existsByName(String name);

  @Query("SELECT COUNT(*) FROM CategoryEntity")
  int getCategoriesCount();

  @Query(
    "SELECT c FROM CategoryEntity c " +
      "WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))" +
      "AND (:featured IS NULL OR c.featured = :featured)" +
      "AND (:categoryIds IS NULL OR c.categoryId IN :categoryIds)")
  Page<CategoryEntity> getCategories(
    @Param("name") String name,
    @Param("featured") Boolean featured,
    @Param("categoryIds") List<Long> categoryIds,
    final Pageable pageable
  );

  Optional<CategoryEntity> getCategoryEntityByCategoryId(Long categoryId);

  boolean existsByCategoryId(Long categoryId);
}
