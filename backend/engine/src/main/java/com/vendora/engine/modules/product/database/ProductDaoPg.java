package com.vendora.engine.modules.product.database;

import com.vendora.engine.modules.product.dao.ProductDao;
import com.vendora.engine.modules.product.database.product.ProductEntity;
import com.vendora.engine.modules.product.database.product.ProductRepository;
import com.vendora.engine.modules.product.database.product_like.ProductLikeEntity;
import com.vendora.engine.modules.product.database.product_like.ProductLikeRepository;
import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.product.model.ProductLike;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Qualifier("postgresql")
public class ProductDaoPg implements ProductDao {
  private final ProductRepository repository;
  private final ProductLikeRepository productLikeRepository;
  private final ModelMapper mapper;

  public ProductDaoPg(
    ProductRepository repository,
    ProductLikeRepository productLikeRepository,
    ModelMapper mapper
  ) {
    this.repository = repository;
    this.productLikeRepository = productLikeRepository;
    this.mapper = mapper;
  }

  @Override
  public Product saveProduct(Product product) {
    var productEntity = mapper.map(product, ProductEntity.class);
    return this.repository.save(productEntity).toModel();
  }

  @Override
  public boolean existProductByName(String name) {
    return this.repository.existsByName(name);
  }

  @Override
  public Page<Product> getProducts(
    String name,
    Boolean enabled,
    Boolean featured,
    List<Long> categoryIds,
    Double minPrice,
    Double maxPrice,
    Integer page,
    Integer size
  ) {
    return this.repository.getProducts(
      Objects.isNull(name) ? "" : name,
      enabled,
      featured,
      categoryIds,
      minPrice,
      maxPrice,
      PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"))
    ).map(ProductEntity::toModel);
  }

  @Override
  public Optional<Product> getProductById(Long productId) {
    return this.repository.findById(productId).map(ProductEntity::toModel);
  }

  @Override
  public boolean existProductById(Long productId) {
    return this.repository.existsByProductId(productId);
  }

  @Override
  public void deleteProductsById(List<Long> productIds) {
    this.repository.deleteAllById(productIds);
  }

  @Override
  public Optional<ProductLike> getProductLikeByProductIdAndUserId(Long productId, Long userId) {
    return this.productLikeRepository.findByProductIdAndUserId(productId, userId).map(ProductLikeEntity::toModel);
  }

  @Override
  public void saveProductLike(ProductLike productLike) {
    var productLikeEntity = mapper.map(productLike, ProductLikeEntity.class);
    this.productLikeRepository.save(productLikeEntity);
  }

  @Override
  public void deleteProductLikesById(List<Long> productLikeIds) {
    this.productLikeRepository.deleteAllById(productLikeIds);
  }
}
