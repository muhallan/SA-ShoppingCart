package order.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import order.domain.Order;
import order.domain.Product;
import order.domain.ShoppingCart;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class CheckoutSubscriber {
    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = {"CheckoutTopic"},groupId = "checkout")
    public void receive(@Payload String message) {
        System.out.println("Receiver received message= "+ message);

        ObjectMapper mapper = new ObjectMapper();
        try {
       	        	
        	ShoppingCart cart = mapper.readValue(message, ShoppingCart.class);
        	
            Order order = new Order();
            order.setCustomerID(cart.getCustomerId().toString());
            order.setOrderNumber(UUID.randomUUID().toString());
            
            order.setOrderLines(new ArrayList<Product>(cart.getCartLines() == null ? null : cart.getCartLines().values()));
            orderService.add(order);

            System.out.println("Order saved with OrderNumber = "+ order.getOrderNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}