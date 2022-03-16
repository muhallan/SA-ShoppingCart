package com.restapplication;

import com.restapplication.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestApplication implements CommandLineRunner {

    @Autowired
    private RestOperations restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String customerUrl = "http://localhost:8081/customer";
        String productUrl = "http://localhost:8091/products";
        String orderUrl = "http://localhost:8080/orders";
        String shoppingCartUrl = "http://localhost:8080/carts";
        String shoppingCartServiceUrl = "http://localhost:8085/carts";

        /**** 1. Add a number of products in the product service ****/
        //add product1
        restTemplate.postForLocation(productUrl, new Product(
                "product1",
                "1",
                10000.0,
                "First product",
                100
        ));

        //add product2
        restTemplate.postForLocation(productUrl, new Product(
                "2",
                "Product 2",
                5000.0,
                "Second product",
                150
        ));

        // get product1
        Product product1 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "product1");
        System.out.println("----------- get product1-----------------------");
        System.out.println(product1);
        // get product2
        Product product2 = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "product2");
        System.out.println("----------- get product2-----------------------");
        System.out.println(product2);

        /**** 2. Modify a product in the productservice ****/
        //modify product1
        product1.setNumberInStock(200);
        restTemplate.put(productUrl + "/{productNumber}", product1, "product1");

        /**** 3. Retrieve one or more products and show these products ****/
        //get modified product
        Product modifyProduct = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "product1");
        System.out.println("----------- get modifyProduct1-----------------------");
        System.out.println(modifyProduct);

        // add customer
        Customer customer1 = new Customer(
                "customer1",
                "Suzy",
                "James",
                "1000 street",
                "Fairfield",
                "52557",
                "12345678",
                "suzyjames@gmail.com"
        );
        restTemplate.postForLocation(customerUrl + "/add", customer1);

        /*//getCustomer
        Customer customer = restTemplate.getForObject(customerUrl + "/{cId}", Customer.class, "customer1");
        System.out.println("----------- get customer1-----------------------");
        System.out.println(customer);*/

        /**** 10. Add customer to order ****/
        //create shopping cart for customer1
        Long shoppingCartNumber = (long) 1.0;
        ShoppingCartCustomer shoppingCartCustomer = new ShoppingCartCustomer(customer1.getcId(), shoppingCartNumber);
        restTemplate.postForLocation(shoppingCartUrl, shoppingCartCustomer);

        /**** 4. Put some products in the shoppingcart  ****/
        //put product to shopping cart
        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(
                Long.parseLong(product1.getProductNumber()),
                product1.getNumberInStock(),
                product1.getPrice());
        restTemplate.postForLocation(shoppingCartUrl + "/{cartId}/products", shoppingCartProduct, shoppingCartCustomer);

        /**** 5. Retrieve and show the shoppingcart  ****/
        //get shopping cart
        ShoppingCart shoppingCart = restTemplate.getForObject(
                shoppingCartServiceUrl + "/{cartNumber}", ShoppingCart.class, shoppingCartNumber);
        System.out.println("----------- get shopping cart-----------------------");
        System.out.println(shoppingCart);

        /**** 6. Delete one product from the shoppingcart ****/
        restTemplate.delete(shoppingCartUrl + "/{cartId}/products/{productNumber}", shoppingCartNumber, product1.getProductNumber());

        /**** 7. Change the quantity of one of the products ****/
        restTemplate.put(shoppingCartUrl + "/{cartId}/products/{productNumber}", shoppingCartNumber, product1.getProductNumber());

        /**** 8. Retrieve and show the shoppingcart  ****/
        ShoppingCart shoppingCart1 = restTemplate.getForObject(
                shoppingCartServiceUrl + "/{cartNumber}", ShoppingCart.class, shoppingCartNumber);
        System.out.println("----------- get shopping cart-----------------------");
        System.out.println(shoppingCart1);

        /**** 9. Checkout the shoppingcart  ****/
        String checkoutMessage = restTemplate.getForObject(shoppingCartUrl + "/{cartId}/checkout",  String.class, shoppingCartNumber);
        System.out.println(checkoutMessage);

        /**** 11. Retrieve and show the order  ****/
        //retrieve order
        Order order = restTemplate.getForObject(orderUrl + "/{orderNumber}", Order.class, "order1");
        //show order
        System.out.println("----------- get order1-----------------------");
        System.out.println(order);

    }

    @Bean
    RestOperations restTemplate() {
        return new RestTemplate();
    }
}
