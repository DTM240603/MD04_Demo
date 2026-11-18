package ra.edu.md04.ex01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.edu.md04.ex01.dto.request.ProductRequest;
import ra.edu.md04.ex01.dto.response.ProductResponse;
import ra.edu.md04.ex01.entity.Product;
import ra.edu.md04.ex01.mapper.ProductMapper;
import ra.edu.md04.ex01.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product savedProduct = productRepository.save(productMapper.toEntity(request));
        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public void reduceStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + productId));
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Không đủ hàng cho sản phẩm với id: " + productId);
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

}
