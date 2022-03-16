package com.example.ShoppingCartCommandService.dto;

import com.example.ShoppingCartCommandService.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductForOrderDto {
    private String customerId;
    private List<Product> products;
}
