package com.restapplication.domain;

public class Order {

	private String orderNumber;
	private String productID;
	private Integer quantity;
//	private Double totalPrice;
//	private Double totalTax;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderNumber='" + orderNumber + '\'' +
				", productID='" + productID + '\'' +
				", quantity=" + quantity +
				'}';
	}

	//	public Double getTotalPrice() {
//		return totalPrice;
//	}
//	public void setTotalPrice(Double totalPrice) {
//		this.totalPrice = totalPrice;
//	}
//	public Double getTotalTax() {
//		return totalTax;
//	}
//	public void setTotalTax(Double totalTax) {
//		this.totalTax = totalTax;
//	}
}
