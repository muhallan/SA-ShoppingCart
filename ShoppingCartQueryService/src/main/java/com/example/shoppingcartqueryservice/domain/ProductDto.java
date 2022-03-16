package com.example.shoppingcartqueryservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long cartNumber;
    private Long productNumber;
    private int quantity;
    private double price;
}
