package productservice.domain;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String productNumber;
    private String name;
    private double price;
    private String description;
    private int numberInStock;

}
