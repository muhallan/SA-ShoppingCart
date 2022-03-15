package order.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import order.domain.Order;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CheckoutSubscriber {
    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = {"CheckoutConsumer"},groupId = "checkout")
    public void receive(@Payload String message) {
        System.out.println("Receiver received message= "+ message);

        ObjectMapper mapper = new ObjectMapper();
        try {
            Order order = mapper.readValue(message, Order.class);
            order.setOrderNumber(UUID.randomUUID().toString());
            orderService.add(order);

            System.out.println("Order saved with OrderNumber = "+ order.getOrderNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}