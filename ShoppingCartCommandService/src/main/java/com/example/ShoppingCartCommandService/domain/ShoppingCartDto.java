package com.example.ShoppingCartCommandService.domain;

import lombok.Data;

import java.util.HashMap;

@Data
public class ShoppingCartDto {
    private Long shopingCartNumber;
    private HashMap<Long, Product> cartLines = new HashMap<>();
}
