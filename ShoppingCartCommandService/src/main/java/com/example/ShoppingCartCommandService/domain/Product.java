package com.example.ShoppingCartCommandService.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
    private String productNumber;
    private int quantity;
    private double price;

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
