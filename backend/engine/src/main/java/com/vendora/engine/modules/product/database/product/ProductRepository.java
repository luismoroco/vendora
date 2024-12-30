package com.vendora.engine.modules.product.database.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  boolean existsByName(String name);

  @Query(
    "SELECT p FROM ProductEntity p " +
      "LEFT JOIN p.categories c " +
      "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))" +
      "AND (:enabled IS NULL OR p.enabled = :enabled)" +
      "AND (:featured IS NULL OR p.featured = :featured)" +
      "AND (:categoryIds IS NULL OR c.categoryId IN :categoryIds)" +
      "AND (:minPrice IS NULL OR p.price > :minPrice)" +
      "AND (:maxPrice IS NULL OR p.price < :maxPrice)")
  Page<ProductEntity> getProducts(
    @Param("name") String name,
    @Param("enabled") Boolean enabled,
    @Param("featured") Boolean featured,
    @Param("categoryIds") List<Long> categoryIds,
    @Param("minPrice") Double minPrice,
    @Param("maxPrice") Double maxPrice,
    final Pageable pageable
  );

  boolean existsByProductId(Long productId);

  List<ProductEntity> getAllByProductIdIn(List<Long> productIds);
}
