package productservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @KafkaListener(topics = "${kafka.topic.order_created}")
    public void receive(@Payload String message,
                        @Headers MessageHeaders headers) {

        ObjectMapper mapper = new ObjectMapper();
        ShoppingCart shoppingCart = null;
        try {
            shoppingCart = mapper.readValue(message, ShoppingCart.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        List<CartLine> cartLineList = shoppingCart.getCartLines();
        for (CartLine cartLine : cartLineList) {
            Product product = productService.findProductByNumber(cartLine.getProductNumber());
            if (product == null) {
                continue;
            }
            product.setNumberInStock(product.getNumberInStock() - cartLine.getQuantity());
            productService.addProduct(product);
        }
    }
}
