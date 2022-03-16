package com.restapplication.domain;

public class ShoppingCartProduct {
    private Long productNumber;
    private int quantity;
    private double price;

    public ShoppingCartProduct(Long productNumber, int quantity, double price) {
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
