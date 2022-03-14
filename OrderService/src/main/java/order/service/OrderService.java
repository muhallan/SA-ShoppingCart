package order.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import order.data.OrderRepository;
import order.domain.Order;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;

	public void add(Order order) {
		orderRepository.save(order);
	}

	public void update(Order order) {
		orderRepository.save(order);
	}

	public Order findByOrderNumber(String productNumber) {
		return orderRepository.findByOrderNumber(productNumber);
	}

	public void delete(String productNumber) {
		Order contact = orderRepository.findByOrderNumber(productNumber);
		orderRepository.delete(contact);
	}

	public Collection<Order> findAll() {
		return orderRepository.findAll();
	}
}