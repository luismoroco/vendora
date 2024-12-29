package com.vendora.engine.modules.category.web.rest;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.modules.category.CategoryUseCase;
import com.vendora.engine.modules.category.model.Category;
import com.vendora.engine.modules.category.web.rest.validator.CreateCategoryRestRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Valid @RequestBody final CreateCategoryRestRequest payload
  ) {
    var category = this.useCase.createCategory(payload.buildRequest());
    return ResponseEntity.status(HttpStatus.CREATED).body(category);
  }
}
