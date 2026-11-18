package ra.edu.md04.ex01.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.edu.md04.ex01.dto.response.ApiResponse;
import ra.edu.md04.ex01.dto.response.ProductResponse;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventory/{productId}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("productId") Long productId);

    @PutMapping("/api/inventory/{productId}/reduce")
    void reduceStock(@PathVariable("productId") Long productId, @RequestParam("quantity") Integer quantity);
}
