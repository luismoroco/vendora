package com.vendora.engine.modules.shopping_cart.dao;

import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartDao {
  ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);
  Optional<ShoppingCart> getShoppingCartByUserId(Long userId);
}
