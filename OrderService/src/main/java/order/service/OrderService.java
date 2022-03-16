package order.service;

import order.data.OrderRepository;
import order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<Order> findByCustomerID(String customerID) {
		return orderRepository.findByCustomerID(customerID);
	}

	public void delete(String productNumber) {
		Order contact = orderRepository.findByOrderNumber(productNumber);
		orderRepository.delete(contact);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}
}
