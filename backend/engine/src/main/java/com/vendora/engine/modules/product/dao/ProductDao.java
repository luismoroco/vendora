package com.vendora.engine.modules.product.dao;

import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.product.model.ProductLike;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductDao {
  Product saveProduct(Product product);

  boolean existProductByName(String name);

  Page<Product> getProducts(
    String name,
    Boolean enabled,
    Boolean featured,
    List<Long> categoryIds,
    Double minPrice,
    Double maxPrice,
    Integer page,
    Integer size
  );

  Optional<Product> getProductById(Long productId);

  boolean existProductById(Long productId);

  void deleteProductsById(List<Long> productIds);

  Optional<ProductLike> getProductLikeByProductIdAndUserId(Long productId, Long userId);

  void saveProductLike(ProductLike productLike);

  void deleteProductLikesById(List<Long> productLikeIds);

  List<Product> getProductsById(List<Long> productIds);
}
