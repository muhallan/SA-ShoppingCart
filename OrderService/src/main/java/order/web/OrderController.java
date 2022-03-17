package order.web;

import order.domain.Order;
import order.domain.Orders;
import order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@GetMapping("/orders/{orderNumber}")
	public ResponseEntity<?> getOrder(@PathVariable String orderNumber) {
		log.info("GET request for /orders/" + orderNumber);
		Order contact = orderService.findByOrderNumber(orderNumber);
		if (contact == null) {
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Order with number = " + orderNumber + " is not available"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(contact, HttpStatus.OK);
	}

	@GetMapping("/orders/customers/{customerID}")
	public ResponseEntity<?> getOrderByCustomerID(@PathVariable String customerID) {
		log.info("GET request for /orders/customers/" + customerID);
		List<Order> orders = orderService.findByCustomerID(customerID);

		if (orders == null) {
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Order with customerID = " + customerID + " is not available"),
					HttpStatus.NOT_FOUND);
		}

		Orders response = new Orders();
		response.setOrders(orders);
		return new ResponseEntity<Orders>(response, HttpStatus.OK);
	}

	@PostMapping("/orders")
	public ResponseEntity<?> addOrder(@RequestBody Order order) {
		log.info("POST request for /orders with body: " + order);
		orderService.add(order);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@GetMapping("/orders")
	public ResponseEntity<?> getAllOrders() {
		log.info("GET request for /orders");
		Orders orders = new Orders(orderService.findAll());
		return new ResponseEntity<Orders>(orders, HttpStatus.OK);
	}
}
