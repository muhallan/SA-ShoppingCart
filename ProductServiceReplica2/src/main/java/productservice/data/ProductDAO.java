package productservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import productservice.domain.Product;

public interface ProductDAO extends MongoRepository<Product, String> {

    Product findByProductNumber(String productNumber);
}
