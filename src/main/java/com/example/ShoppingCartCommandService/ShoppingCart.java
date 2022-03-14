package com.example.ShoppingCartCommandService;

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
    private Long shopingCartNumber;
    private Long customerId;
    private HashMap<Long,ProductDto> cartLines = new HashMap<>();


    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shopingCartNumber=" + shopingCartNumber +
                ", customerId=" + customerId +
                ", cartLines=" + cartLines +
                '}';
    }
}
