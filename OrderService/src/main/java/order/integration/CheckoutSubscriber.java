package order.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import order.domain.CustomerInfo;
import order.domain.Order;
import order.domain.ShoppingCart;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Service
public class CheckoutSubscriber {
	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerFeignClient customerFeignClient;

	@KafkaListener(topics = { "${kafka.checkout_topic_name}" }, groupId = "checkout")
	public void receive(@Payload String message) {

		System.out.println("Receiver received message= " + message);

		ObjectMapper mapper = new ObjectMapper();

		try {

			ShoppingCart cart = mapper.readValue(message, ShoppingCart.class);
			CustomerInfo customer = null;

			try {
				customer = customerFeignClient.getCustomer(cart.getCustomerId().toString());
			} catch(Exception e)
			{
				e.printStackTrace();
			}

			Order order = new Order();
			order.setCustomerID(cart.getCustomerId().toString());
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setCustomerInfo(customer);
			if(cart.getProducts() != null)
				order.setOrderLines(cart.getProducts());
			orderService.add(order);

			System.out.println("Successfully saved.\n" + mapper.writeValueAsString(order));

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
		public CustomerInfo getCustomer(@PathVariable String customerId);
	}
	@FeignClient("product-service")
	interface ProductFeignClient {

		@RequestMapping("products/{productId}")
		public CustomerInfo getProduct(@PathVariable String productId);
	}
}