package ra.edu.md04.ex01.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ra.edu.md04.ex01.client.InventoryClient;
import ra.edu.md04.ex01.dto.request.OrderRequest;
import ra.edu.md04.ex01.dto.response.OrderResponse;
import ra.edu.md04.ex01.dto.response.ProductResponse;
import ra.edu.md04.ex01.entity.Order;
import ra.edu.md04.ex01.mapper.OrderMapper;
import ra.edu.md04.ex01.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderMapper orderMapper;

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public OrderResponse createOrder(OrderRequest request) {
        // 1. Gọi sang Inventory Service để lấy thông tin sản phẩm
        ProductResponse product;
        try {
            product = inventoryClient.getProductById(request.getProductId()).getData();
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại trong kho!");
        }

        // 2. Tính tổng tiền
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        // 3. Kiểm tra tồn kho
        if (product.getQuantity() < request.getQuantity()) {
            Order cancelledOrder = orderMapper.toEntity(request, "CANCELLED", totalPrice);
            Order savedCancelledOrder = orderRepository.save(cancelledOrder);
            return orderMapper.toResponse(savedCancelledOrder);
        }

        // 4. Trừ tồn kho bên Inventory Service & lưu đơn hàng COMPLETED
        inventoryClient.reduceStock(request.getProductId(), request.getQuantity());

        Order completedOrder = orderMapper.toEntity(request, "COMPLETED", totalPrice);
        Order savedCompletedOrder = orderRepository.save(completedOrder);
        return orderMapper.toResponse(savedCompletedOrder);
    }
}
