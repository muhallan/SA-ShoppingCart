package com.restapplication.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
public class ShoppingCart {
    @Id
    private Long shoppingCartNumber;
    private String customerId;
    private HashMap<Long,ProductDto> cartLines = new HashMap<>();

    public ShoppingCart() {
    }

    public ShoppingCart(Long shopingCartNumber, String customerId, HashMap<Long, ProductDto> cartLines) {
        this.shoppingCartNumber = shopingCartNumber;
        this.customerId = customerId;
        this.cartLines = cartLines;
    }

    public Long getShopingCartNumber() {
        return shoppingCartNumber;
    }

    public void setShopingCartNumber(Long shopingCartNumber) {
        this.shoppingCartNumber = shopingCartNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
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
                "shoppingCartNumber=" + shoppingCartNumber +
                ", customerId=" + customerId +
                ", cartLines=" + cartLines +
                '}';
    }
}
