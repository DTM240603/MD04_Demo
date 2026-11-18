package ra.edu.md04.ex01.mapper;

import org.springframework.stereotype.Component;
import ra.edu.md04.ex01.dto.request.ProductRequest;
import ra.edu.md04.ex01.dto.response.ProductResponse;
import ra.edu.md04.ex01.entity.Product;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequest request) {
        return Product.builder()
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
}
