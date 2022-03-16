package order.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Order {

	@Id
	private String orderNumber;
	private String customerID;
	private Customer customer;
	private List<Product> orderLines;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<Product> orderLines) {
		this.orderLines = orderLines;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerId) {
		this.customerID = customerId;
	}
}
