package com.restapplication.domain;

public class ShoppingCartCustomer {
    private String customerId;
    private Long cartNumber;

    public ShoppingCartCustomer(String customerId, Long cartNumber) {
        this.customerId = customerId;
        this.cartNumber = cartNumber;
    }
}
