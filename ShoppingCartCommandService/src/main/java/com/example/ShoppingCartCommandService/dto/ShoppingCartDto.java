package com.example.ShoppingCartCommandService.dto;

import com.example.ShoppingCartCommandService.domain.Product;
import lombok.Data;

import java.util.HashMap;

@Data
public class ShoppingCartDto {
    private Long shoppingCartNumber;
    private HashMap<Long, Product> cartLines = new HashMap<>();
}
