package productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import productservice.domain.CartLine;
import productservice.domain.Product;
import productservice.domain.ShoppingCart;

import java.util.List;

@SpringBootApplication
@EnableKafka
public class TestClientApplication implements CommandLineRunner {

    @Autowired
    private Sender sender;
    @Value("${app.topic.order_created}")
    private String order_created;

    @Autowired
    private RestOperations restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TestClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CartLine cartLine = new CartLine(3, "1234");
        CartLine cartLine1 = new CartLine(2, "7893");
        CartLine cartLine2 = new CartLine(4, "8792");
        CartLine cartLine3 = new CartLine(5, "9082");

        ShoppingCart cart = new ShoppingCart();
        List<CartLine> cartLines = List.of(cartLine, cartLine1, cartLine2, cartLine3);
        cart.setCartLines(cartLines);

        Product product = new Product("1234", "Camp", 67.33, "test", 63);
        Product product1 = new Product("7893", "Cola", 42.42, "test", 23);
        Product product2 = new Product("8792", "Pillow", 93.22, "test", 45);
        Product product3 = new Product("9082", "Jolis", 54.33, "test", 32);
        Product product4 = new Product("7682", "Camp", 67.33, "test", 63);
        Product product5 = new Product("9429", "Camp", 67.33, "test", 63);

        String productsServerUrl = "http://localhost:8091/products";
        // add products
        restTemplate.postForLocation(productsServerUrl, product);
        restTemplate.postForLocation(productsServerUrl, product1);
        restTemplate.postForLocation(productsServerUrl, product2);
        restTemplate.postForLocation(productsServerUrl, product3);
        restTemplate.postForLocation(productsServerUrl, product4);
        restTemplate.postForLocation(productsServerUrl, product5);

        // send a shopping cart created event
        sender.send(order_created, cart);
    }

    @Bean
    RestOperations restTemplate() {
        return new RestTemplate();
    }
}
