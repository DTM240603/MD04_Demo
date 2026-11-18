package ra.edu.md04.ex01.mapper;

import org.springframework.stereotype.Component;
import ra.edu.md04.ex01.dto.request.OrderRequest;
import ra.edu.md04.ex01.dto.response.OrderResponse;
import ra.edu.md04.ex01.entity.Order;

import java.math.BigDecimal;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequest request, String status, BigDecimal totalPrice) {
        if (request == null) return null;
        return Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(status)
                .totalPrice(totalPrice)
                .build();
    }

    public OrderResponse toResponse(Order entity) {
        if (entity == null) return null;
        return OrderResponse.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .status(entity.getStatus())
                .totalPrice(entity.getTotalPrice())
                .build();
    }
}
