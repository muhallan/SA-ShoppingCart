package com.example.ShoppingCartCommandService.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashMap;


@Data
@NoArgsConstructor
@Document
public class ShoppingCart {
    @Id
    private Long shoppingCartNumber;
    private Long customerId;
    private HashMap<Long, Product> cartLines = new HashMap<>();


    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartNumber=" + shoppingCartNumber +
                ", customerId=" + customerId +
                ", cartLines=" + cartLines +
                '}';
    }
}
