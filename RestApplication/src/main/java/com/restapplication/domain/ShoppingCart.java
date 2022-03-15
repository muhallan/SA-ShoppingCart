package com.restapplication.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
public class ShoppingCart {
    @Id
    private Long shopingCartNumber;
    private Long customerId;
    private HashMap<Long,ProductDto> cartLines = new HashMap<>();

    public ShoppingCart() {
    }

    public ShoppingCart(Long shopingCartNumber, Long customerId, HashMap<Long, ProductDto> cartLines) {
        this.shopingCartNumber = shopingCartNumber;
        this.customerId = customerId;
        this.cartLines = cartLines;
    }

    public Long getShopingCartNumber() {
        return shopingCartNumber;
    }

    public void setShopingCartNumber(Long shopingCartNumber) {
        this.shopingCartNumber = shopingCartNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public HashMap<Long, ProductDto> getCartLines() {
        return cartLines;
    }

    public void setCartLines(HashMap<Long, ProductDto> cartLines) {
        this.cartLines = cartLines;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shopingCartNumber=" + shopingCartNumber +
                ", customerId=" + customerId +
                ", cartLines=" + cartLines +
                '}';
    }
}
