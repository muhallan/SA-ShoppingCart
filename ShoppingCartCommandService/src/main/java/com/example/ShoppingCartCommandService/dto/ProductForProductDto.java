package com.example.ShoppingCartCommandService.dto;

import com.example.ShoppingCartCommandService.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductForProductDto {
    private List<Product> products;
}
