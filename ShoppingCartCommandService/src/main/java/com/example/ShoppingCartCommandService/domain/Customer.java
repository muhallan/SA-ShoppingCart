package com.example.ShoppingCartCommandService.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
    private String customerId;
    private Long cartNumber;
}
