package com.example.ShoppingCartCommandService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductDto {
    private Long productNumber;
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
