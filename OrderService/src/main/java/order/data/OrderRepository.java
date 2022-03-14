package order.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import order.domain.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByOrderNumber(String orderNumber);
}
