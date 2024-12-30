package com.vendora.engine.modules.shopping_cart;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.modules.product.dao.ProductDao;
import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.shopping_cart.dao.ShoppingCartDao;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCart;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCartItem;
import com.vendora.engine.modules.shopping_cart.request.GetShoppingCartRequest;
import com.vendora.engine.modules.shopping_cart.request.ShoppingCartItemRequest;
import com.vendora.engine.modules.shopping_cart.request.UpdateShoppingCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ShoppingCartUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartUseCase.class);
  private final ShoppingCartDao dao;
  private final ProductDao productDao;

  public ShoppingCartUseCase(
    @Qualifier("postgresql") ShoppingCartDao dao,
    @Qualifier("postgresql") ProductDao productDao
  ) {
    this.dao = dao;
    this.productDao = productDao;
  }

  public ShoppingCart updateShoppingCart(final UpdateShoppingCartRequest request) {
    var shoppingCart = this.dao.getShoppingCartByUserId(request.getUserId())
      .orElseGet(() -> createEmptyShoppingCart(request.getUserId()));

    this.addItems(shoppingCart, request.getItems());
    return this.dao.saveShoppingCart(shoppingCart);
  }

  public ShoppingCart getShoppingCart(final GetShoppingCartRequest request) {
    var shoppingCart = this.dao.getShoppingCartByUserId(request.getUserId())
      .orElseGet(() -> createEmptyShoppingCart(request.getUserId()));

    if (Objects.isNull(shoppingCart.getShoppingCartId())) {
      shoppingCart = this.dao.saveShoppingCart(shoppingCart);
    }

    return shoppingCart;
  }

  private void addItems(ShoppingCart shoppingCart, List<ShoppingCartItemRequest> itemRequests) {
    var productIds = itemRequests.stream()
      .map(ShoppingCartItemRequest::getProductId)
      .toList();

    var products = this.productDao.getProductsById(productIds).stream()
      .collect(Collectors.toMap(Product::getProductId, product -> product));

    var items = new HashSet<ShoppingCartItem>(itemRequests.size());
    for (int index = 0; index < itemRequests.size(); index++) {
      var request = itemRequests.get(index);

      var product = products.get(request.getProductId());
      if (Objects.isNull(product)) {
        continue;
      }

      if (product.getStock() < request.getQuantity()) {
        throw new BadRequestException("Insufficient stock");
      }

      var item = new ShoppingCartItem();
      item.setProductId(product.getProductId());
      item.setQuantity(request.getQuantity());
      item.setNumber(index);
      item.setShoppingCart(shoppingCart);

      items.add(item);
    }

    shoppingCart.setItems(items);
  }

  private static ShoppingCart createEmptyShoppingCart(Long userId) {
    var shoppingCart = new ShoppingCart();
    shoppingCart.setUserId(userId);
    shoppingCart.setItems(new HashSet<>());

    return shoppingCart;
  }
}
