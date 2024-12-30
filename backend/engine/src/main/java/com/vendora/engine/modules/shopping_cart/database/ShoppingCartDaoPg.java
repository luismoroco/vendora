package com.vendora.engine.modules.shopping_cart.database;

import com.vendora.engine.modules.shopping_cart.dao.ShoppingCartDao;
import com.vendora.engine.modules.shopping_cart.database.shopping_cart.ShoppingCartEntity;
import com.vendora.engine.modules.shopping_cart.database.shopping_cart.ShoppingCartRepository;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("postgresql")
public class ShoppingCartDaoPg implements ShoppingCartDao {
  private final ShoppingCartRepository repository;
  private final ModelMapper mapper;

  public ShoppingCartDaoPg(
    ShoppingCartRepository repository,
    ModelMapper mapper
  ) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
    var shoppingCartEntity = mapper.map(shoppingCart, ShoppingCartEntity.class);
    return this.repository.save(shoppingCartEntity).toModel();
  }

  @Override
  public Optional<ShoppingCart> getShoppingCartByUserId(Long userId) {
    return this.repository.findByUserId(userId).map(ShoppingCartEntity::toModel);
  }
}
