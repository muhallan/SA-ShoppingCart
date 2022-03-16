package com.example.ShoppingCartCommandService.dto;

import lombok.Data;

@Data
public class ProductForStock {
    private String productNumber;
    private String name;
    private double price;
    private String description;
    private int numberInStock;
}
