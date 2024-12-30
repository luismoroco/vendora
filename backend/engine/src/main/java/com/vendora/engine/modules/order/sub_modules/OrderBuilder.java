package com.vendora.engine.modules.order.sub_modules;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.modules.currency.model.Currency;
import com.vendora.engine.modules.order.model.Order;
import com.vendora.engine.modules.order.model.OrderItem;
import com.vendora.engine.modules.order.model.OrderStatusType;
import com.vendora.engine.modules.product.dao.ProductDao;
import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.shopping_cart.dao.ShoppingCartDao;
import com.vendora.engine.modules.shopping_cart.model.ShoppingCartItem;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderBuilder {
  private final ShoppingCartDao shoppingCartDao;
  private final ProductDao productDao;
  private Long userId;
  private Set<OrderItem> orderItems = new HashSet<>();
  private OrderStatusType orderStatusType;
  private Currency currency;
  private Double amount = 0.0;
  private Order order;

  public OrderBuilder(
    ShoppingCartDao shoppingCartDao,
    ProductDao productDao
  ) {
    this.shoppingCartDao = shoppingCartDao;
    this.productDao = productDao;
  }

  public OrderBuilder builder() {
    this.order = new Order();

    return this;
  }

  public OrderBuilder addUser(Long userId) {
    this.userId = userId;

    return this;
  }

  public OrderBuilder addStatus(OrderStatusType statusType) {
    this.orderStatusType = statusType;

    return this;
  }

  public OrderBuilder addCurrency(Currency currency) {
    this.currency = currency;

    return this;
  }

  public OrderBuilder addItems() {
    var shoppingCart = this.shoppingCartDao.getShoppingCartByUserId(this.userId)
      .orElseThrow(
        () -> new BadRequestException("Shopping cart not found")
      );

    var productIds = shoppingCart.getItems().stream()
      .map(ShoppingCartItem::getProductId)
      .toList();

    var productMap = this.productDao.getProductsById(productIds).stream()
      .collect(Collectors.toMap(Product::getProductId, product -> product));

    for (var shoppingCartItem : shoppingCart.getItems()) {
      var product = productMap.get(shoppingCartItem.getProductId());
      if (Objects.isNull(product)) {
        continue;
      }

      var orderItem = new OrderItem();
      orderItem.setProductId(product.getProductId());
      orderItem.setName(product.getName());
      orderItem.setUnitPrice(product.getPrice());
      orderItem.setQuantity(shoppingCartItem.getQuantity());
      orderItem.setOrder(this.order);
      this.amount += shoppingCartItem.getQuantity() * product.getPrice();

      this.orderItems.add(orderItem);
    }

    return this;
  }

  public Order build() {
    this.order.setUserId(this.userId);
    this.order.setOrderStatusType(this.orderStatusType);
    this.order.setCurrency(this.currency);
    this.order.setAmount(this.amount);
    this.order.setItems(this.orderItems);

    return order;
  }
}
