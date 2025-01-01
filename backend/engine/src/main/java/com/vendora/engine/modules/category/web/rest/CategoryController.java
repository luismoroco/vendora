package com.vendora.engine.modules.category.web.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.category.CategoryUseCase;
import com.vendora.engine.modules.category.model.Category;
import com.vendora.engine.modules.category.request.DeleteCategoryRequest;
import com.vendora.engine.modules.category.request.GetCategoryByIdRequest;
import com.vendora.engine.modules.category.web.rest.validator.CreateCategoryWebRequest;
import com.vendora.engine.modules.category.web.rest.validator.GetCategoriesWebRequest;
import com.vendora.engine.modules.category.web.rest.validator.UpdateCategoryWebRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
  private final CategoryUseCase useCase;
  private final Scrooge<? extends Credentials> scrooge;

  public CategoryController(
    CategoryUseCase useCase,
    @Qualifier("Jwt") Scrooge<? extends Credentials> scrooge
  ) {
    this.useCase = useCase;
    this.scrooge = scrooge;
  }

  @PostMapping("")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Category> createCategory(
    @Valid @RequestBody final CreateCategoryWebRequest payload
  ) {
    var category = this.useCase.createCategory(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.CREATED).body(category);
  }

  @GetMapping("")
  public ResponseEntity<Page<Category>> getCategories(@Valid final GetCategoriesWebRequest payload) {
    var categories = this.useCase.getCategories(payload.buildRequest());

    return ResponseEntity.status(HttpStatus.OK).body(categories);
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<Category> getCategoryById(@PathVariable final Long categoryId) {
    var request = new GetCategoryByIdRequest(categoryId);
    var category = this.useCase.getCategoryById(request);

    return ResponseEntity.status(HttpStatus.OK).body(category);
  }

  @PutMapping("/{categoryId}")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Category> updateCategory(
    @Valid @RequestBody final UpdateCategoryWebRequest payload,
    @PathVariable final Long categoryId
  ) {
    var category = this.useCase.updateCategory(
      payload.buildRequest(
        Map.of("categoryId", categoryId)
      )
    );

    return ResponseEntity.status(HttpStatus.OK).body(category);
  }

  @DeleteMapping("/{categoryId}")
  @PreAuthorize("hasRole('MANAGER')")
  public ResponseEntity<Void> deleteCategory(@PathVariable final Long categoryId) {
    var request = new DeleteCategoryRequest(categoryId);
    this.useCase.deleteCategoryById(request);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
