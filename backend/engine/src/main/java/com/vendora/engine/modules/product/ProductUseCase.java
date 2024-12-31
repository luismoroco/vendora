package com.vendora.engine.modules.product;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.modules.category.dao.CategoryDao;
import com.vendora.engine.modules.product.dao.ProductDao;
import com.vendora.engine.modules.product.model.Product;
import com.vendora.engine.modules.product.model.ProductImage;
import com.vendora.engine.modules.product.model.ProductLike;
import com.vendora.engine.modules.product.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
public class ProductUseCase {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductUseCase.class);
  private final ProductDao dao;
  private final CategoryDao categoryDao;
  @Value("${application.constraints.product.max-images}")
  private Integer MAX_IMAGES_PER_PRODUCT;

  public ProductUseCase(
    @Qualifier("postgresql") ProductDao dao,
    @Qualifier("postgresql") CategoryDao categoryDao
  ) {
    this.dao = dao;
    this.categoryDao = categoryDao;
  }

  private static void addProductImages(Product product, List<CreateProductImageRequest> imageRequests) {
    int imagesLength = imageRequests.size();
    var images = new HashSet<ProductImage>(imagesLength);
    for (int index = 0; index < imagesLength; index++) {
      var imageRequest = imageRequests.get(index);

      var image = new ProductImage();
      image.setUrl(imageRequest.getUrl());
      image.setNumber(index);
      image.setProduct(product);

      images.add(image);
    }

    product.setImages(images);
  }

  public Product createProduct(final CreateProductRequest request) {
    int imagesLength = request.getImages().size();
    this.validateProductConstraints(imagesLength, request.getName());

    var categories = this.categoryDao.getCategoriesById(request.getCategoryIds());
    var product = new Product();
    product.setName(request.getName());
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setEnabled(request.getEnabled());
    product.setArchived(Boolean.FALSE);
    product.setFeatured(request.getFeatured());
    product.setStock(request.getStock());
    product.setImages(new HashSet<>());
    product.setCategories(categories);

    addProductImages(product, request.getImages());

    return this.dao.saveProduct(product);
  }

  public Page<Product> getProducts(final GetProductsRequest request) {
    return this.dao.getProducts(
      request.getName(),
      request.getEnabled(),
      request.getFeatured(),
      request.getCategoryIds(),
      request.getMinPrice(),
      request.getMaxPrice(),
      request.getPage(),
      request.getSize()
    );
  }

  public Product updateProduct(final UpdateProductRequest request) {
    var imagesLength = Objects.isNull(request.getImages()) ? null : request.getImages().size();
    this.validateProductConstraints(imagesLength, request.getName());

    var product = this.dao.getProductById(request.getProductId())
      .orElseThrow(
        () -> new NotFoundException("Product not found")
      );

    if (Objects.nonNull(request.getName())) {
      product.setName(request.getName());
    }

    if (Objects.nonNull(request.getDescription())) {
      product.setDescription(request.getDescription());
    }

    if (Objects.nonNull(request.getPrice())) {
      product.setPrice(request.getPrice());
    }

    if (Objects.nonNull(request.getEnabled())) {
      product.setEnabled(request.getEnabled());
    }

    if (Objects.nonNull(request.getFeatured())) {
      product.setFeatured(request.getFeatured());
    }

    if (Objects.nonNull(request.getArchived())) {
      product.setArchived(request.getArchived());
    }

    if (Objects.nonNull(request.getStock())) {
      product.setStock(request.getStock());
    }

    if (Objects.nonNull(request.getImages())) {
      addProductImages(product, request.getImages());
    }

    return this.dao.saveProduct(product);
  }

  public void deleteProductById(final DeleteProductByIdRequest request) {
    if (!this.dao.existProductById(request.getProductId())) {
      throw new NotFoundException("Category not found");
    }

    this.dao.deleteProductsById(List.of(request.getProductId()));
  }

  public void putLikeProduct(final LikeProductRequest request) {
    var like = this.dao.getProductLikeByProductIdAndUserId(request.getProductId(), request.getUserId());
    if (like.isPresent()) {
      this.dao.deleteProductLikesById(List.of(like.get().getProductLikeId()));
    } else {
      this.dao.saveProductLike(
        ProductLike.builder()
          .productId(request.getProductId())
          .userId(request.getUserId())
          .build()
      );
    }
  }

  private void validateProductConstraints(Integer imagesLength, String productName) {
    if (Objects.nonNull(imagesLength)) {
      if (imagesLength > MAX_IMAGES_PER_PRODUCT) {
        throw new BadRequestException(
          "Product images exceeded [imagesLength=%d][maxAllowed=%d]"
            .formatted(imagesLength, MAX_IMAGES_PER_PRODUCT)
        );
      }
    }

    if (Objects.nonNull(productName)) {
      if (this.dao.existProductByName(productName)) {
        throw new BadRequestException("Product already exists");
      }
    }
  }
}
