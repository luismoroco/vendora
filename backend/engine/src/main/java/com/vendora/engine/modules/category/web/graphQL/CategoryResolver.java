package com.vendora.engine.modules.category.web.graphQL;

import com.vendora.engine.modules.category.CategoryUseCase;
import com.vendora.engine.modules.category.model.Category;
import com.vendora.engine.modules.category.request.DeleteCategoryRequest;
import com.vendora.engine.modules.category.request.GetCategoryByIdRequest;
import com.vendora.engine.modules.category.web.validator.CreateCategoryWebRequest;
import com.vendora.engine.modules.category.web.validator.UpdateCategoryWebRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class CategoryResolver {
  private final CategoryUseCase useCase;

  public CategoryResolver(
    CategoryUseCase useCase
  ) {
    this.useCase = useCase;
  }

  @MutationMapping
  public Category createCategory(@Argument final CreateCategoryWebRequest input) {
    return this.useCase.createCategory(input.buildRequest());
  }

  @QueryMapping
  public Category getCategoryById(@Argument final Long categoryId) {
    var request = new GetCategoryByIdRequest(categoryId);

    return this.useCase.getCategoryById(request);
  }

  @MutationMapping
  public Category updateCategoryById(
    @Argument final UpdateCategoryWebRequest input,
    @Argument final Long categoryId
  ) {
    return this.useCase.updateCategory(
      input.buildRequest(
        Map.of("categoryId", categoryId)
      )
    );
  }

  @MutationMapping
  public Boolean deleteCategoryById(@Argument final Long categoryId) {
    var request = new DeleteCategoryRequest(categoryId);

    this.useCase.deleteCategoryById(request);
    return Boolean.TRUE;
  }
}
