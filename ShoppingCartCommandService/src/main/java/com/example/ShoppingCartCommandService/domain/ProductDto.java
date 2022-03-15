package com.example.ShoppingCartCommandService.domain;

import lombok.Data;

@Data
public class ProductDto {
    private Long cartNumber;
    private Long productNumber;
    private int quantity;
    private double price;
}
