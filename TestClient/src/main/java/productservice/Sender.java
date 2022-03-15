package productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import productservice.domain.ShoppingCart;

@Service
public class Sender {
    @Autowired
    private KafkaTemplate<String, ShoppingCart> kafkaTemplate;

    public void send(String topic, ShoppingCart shoppingCart){
        System.out.println("sending shopping cart to topic: " + topic);
        kafkaTemplate.send(topic, shoppingCart);
    }
}
