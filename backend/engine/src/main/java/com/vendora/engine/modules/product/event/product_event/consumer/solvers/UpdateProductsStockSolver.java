package com.vendora.engine.modules.product.event.product_event.consumer.solvers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.order.OrderUseCase;
import com.vendora.engine.modules.order.request.GetOrderByIdRequest;
import com.vendora.engine.modules.product.ProductUseCase;
import com.vendora.engine.modules.product.event.product_event.ProductEvent;
import com.vendora.engine.modules.product.request.UpdateProductRequest;
import org.springframework.stereotype.Component;

@Component("update_products_stock")
public class UpdateProductsStockSolver implements EventSolver<ProductEvent> {
  private final ProductUseCase useCase;
  private final OrderUseCase orderUseCase;

  public UpdateProductsStockSolver(
    ProductUseCase useCase,
    OrderUseCase orderUseCase
  ) {
    this.useCase = useCase;
    this.orderUseCase = orderUseCase;
  }

  @Override
  public void execute(ProductEvent event) {
    var order = this.orderUseCase.getOrderById(new GetOrderByIdRequest(event.getOrderId()));
    order.getItems().forEach(
      orderItem -> {
        var request = new UpdateProductRequest();
        request.setProductId(orderItem.getProductId());
        request.setStock(-1 * orderItem.getQuantity());

        this.useCase.updateProduct(request);
      }
    );
  }
}
