package com.vendora.engine.modules.product.database.product_like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLikeEntity, Long> {
  Optional<ProductLikeEntity> findByProductIdAndUserId(Long productId, Long userId);
}
