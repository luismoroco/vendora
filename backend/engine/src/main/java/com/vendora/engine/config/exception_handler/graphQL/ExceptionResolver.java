package com.vendora.engine.config.exception_handler.graphQL;

import com.vendora.engine.common.error.exc.exception.BadRequestException;
import com.vendora.engine.common.error.exc.exception.NotFoundException;
import com.vendora.engine.common.error.exc.exception.UnauthorizedException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {
  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    if (ex instanceof NotFoundException) {
      return GraphQLError.newError()
        .errorType(ErrorType.NOT_FOUND)
        .message(ex.getMessage())
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .build();
    }

    if (ex instanceof BadRequestException) {
      return GraphQLError.newError()
        .errorType(ErrorType.BAD_REQUEST)
        .message(ex.getMessage())
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .build();
    }

    if (ex instanceof UnauthorizedException) {
      return GraphQLError.newError()
        .errorType(ErrorType.UNAUTHORIZED)
        .message(ex.getMessage())
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .build();
    }

    if (ex instanceof RuntimeException) {
      return GraphQLError.newError()
        .errorType(ErrorType.INTERNAL_ERROR)
        .message(ex.getMessage())
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .build();
    }

    return null;
  }
}
