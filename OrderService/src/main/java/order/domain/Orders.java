package order.domain;

import java.util.Collection;

public class Orders {
	Collection<Order> orders;

	public Orders() {

	}

	public Orders(Collection<Order> orders) {
		this.orders = orders;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
}
