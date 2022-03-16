package order.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import order.domain.Customer;
import order.domain.Order;
import order.domain.Product;
import order.domain.ShoppingCart;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class CheckoutSubscriber {
	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerFeignClient customerFeignClient;

	@KafkaListener(topics = { "CheckoutTopic" }, groupId = "checkout")
	public void receive(@Payload String message) {

		System.out.println("Receiver received message= " + message);

		ObjectMapper mapper = new ObjectMapper();

		try {

			ShoppingCart cart = mapper.readValue(message, ShoppingCart.class);
			Customer customer = customerFeignClient.getCustomer(cart.getCustomerId().toString());

			Order order = new Order();
			order.setCustomerID(cart.getCustomerId().toString());
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setCustomer(customer);
			if(cart.getCartLines() != null)
			order.setOrderLines(
					new ArrayList<Product>( cart.getCartLines().values()));
			orderService.add(order);

			System.out.println("Order saved with OrderNumber = " + order.getOrderNumber());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void timeoutFallBack()
	{
		//do logging
	}

	@FeignClient("customer-service")
	interface CustomerFeignClient {

		@RequestMapping("customer/{customerId}")
		public Customer getCustomer(@PathVariable String customerId);
	}
}