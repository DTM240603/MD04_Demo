package ra.edu.md04.ex01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.md04.ex01.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
