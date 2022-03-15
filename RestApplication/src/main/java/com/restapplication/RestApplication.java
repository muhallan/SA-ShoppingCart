package com.restapplication;

import com.restapplication.domain.Customer;
import com.restapplication.domain.Order;
import com.restapplication.domain.Product;
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
        String shoppingCartUrl = "http://localhost:8080/cart";

        /*// add customer
        restTemplate.postForLocation(customerUrl + "/add", new Customer(
                "customer1",
                "Suzy",
                "James",
                "1000 street",
                "Fairfield",
                "52557",
                "12345678",
                "suzyjames@gmail.com"
        ));

        //getCustomer
        Customer customer = restTemplate.getForObject(customerUrl + "/{cId}", Customer.class, "customer1");
        System.out.println("----------- get customer1-----------------------");
        System.out.println(customer);*/

        /**** 1 ****/
        //add product1
        restTemplate.postForLocation(productUrl, new Product(
                "product1",
                "Product 1",
                10000.0,
                "First product",
                100
        ));

        //add product2
        restTemplate.postForLocation(productUrl, new Product(
                "product2",
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

        /**** 2 and 7 ****/
        //modify product1
        product1.setNumberInStock(200);
        restTemplate.put(productUrl + "/{productNumber}", product1, "product1");

        /**** 3 ****/
        //get modified product
        Product modifyProduct = restTemplate.getForObject(productUrl + "/{productNumber}", Product.class, "product1");
        System.out.println("----------- get modifyProduct1-----------------------");
        System.out.println(modifyProduct);

        /**** 11 ****/
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
