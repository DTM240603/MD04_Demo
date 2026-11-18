package ra.edu.md04.ex01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.edu.md04.ex01.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
