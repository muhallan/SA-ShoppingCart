package com.example.shoppingcartqueryservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ProductDto {
    private Long cartNumber;
    private Long productNumber;
    private int quantity;
    private double price;
}
