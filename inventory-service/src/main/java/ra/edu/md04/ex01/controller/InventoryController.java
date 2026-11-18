package ra.edu.md04.ex01.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.md04.ex01.dto.request.ProductRequest;
import ra.edu.md04.ex01.dto.response.ApiResponse;
import ra.edu.md04.ex01.dto.response.ProductResponse;
import ra.edu.md04.ex01.service.InventoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findById(@PathVariable Long id) {
        ProductResponse productResponse = inventoryService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(productResponse, "Lấy sản phẩm theo ID thành công"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        ProductResponse createdProduct = inventoryService.createProduct(request);
        return ResponseEntity.status(201).body(ApiResponse.created(createdProduct, "Tạo sản phẩm thành công"));
    }

    @PutMapping("/{productId}/reduce")
    public ResponseEntity<ApiResponse<Void>> reduceStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        inventoryService.reduceStock(productId, quantity);
        return ResponseEntity.ok(ApiResponse.success(null, "Giảm số lượng sản phẩm thành công"));
    }
}
