package com.vendora.engine.modules.shopping_cart.database.shopping_cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
  Optional<ShoppingCartEntity> findByUserId(Long userId);
}
