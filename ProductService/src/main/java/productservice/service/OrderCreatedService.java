package productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import productservice.domain.CartLine;
import productservice.domain.Product;
import productservice.domain.ShoppingCart;

import java.util.List;

@RefreshScope
@Service
public class OrderCreatedService {
    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedService.class);

    @KafkaListener(topics = "${kafka.topic.order_created}")
    public void receive(@Payload String cartString,
                        @Headers MessageHeaders headers) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ShoppingCart cart = mapper.readValue(cartString, ShoppingCart.class);
        List<CartLine> cartLineList = cart.getProducts();

        for (CartLine cartLine: cartLineList) {
            Product product = productService.findProductByNumber(cartLine.getProductNumber());
            if (product == null) {
                log.warn("Received a cartLine with a non-existent product number: " + cartLine.getProductNumber());
                continue;
            }
            product.setNumberInStock(product.getNumberInStock() - cartLine.getQuantity());
            log.info("Reducing the number in stock of product: " + product.getProductNumber() + " to " + product.getNumberInStock());
            productService.addProduct(product);
        }
    }
}
