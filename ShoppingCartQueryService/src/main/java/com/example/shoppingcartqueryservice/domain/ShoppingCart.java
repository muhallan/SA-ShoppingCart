package com.example.shoppingcartqueryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class ShoppingCart {
    @Id
    private Long shoppingCartNumber;
    private String customerId;
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
