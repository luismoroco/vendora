package com.vendora.engine.modules.shopping_cart.database.shopping_cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
}
