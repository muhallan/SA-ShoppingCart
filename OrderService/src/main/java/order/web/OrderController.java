package order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import order.domain.Order;
import order.domain.Orders;
import order.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/orders/{orderNumber}")
	public ResponseEntity<?> getOrder(@PathVariable String orderNumber) {
		Order contact = orderService.findByOrderNumber(orderNumber);
		if (contact == null) {
			return new ResponseEntity<CustomErrorType>(
					new CustomErrorType("Order with number = " + orderNumber + " is not available"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(contact, HttpStatus.OK);
	}

	@PostMapping("/orders")
	public ResponseEntity<?> addOrder(@RequestBody Order order) {
		orderService.add(order);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@GetMapping("/orders")
	public ResponseEntity<?> getAllOrders() {
		Orders orders = new Orders(orderService.findAll());
		return new ResponseEntity<Orders>(orders, HttpStatus.OK);
	}
}
