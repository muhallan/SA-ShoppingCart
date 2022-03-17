package productservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Product {
    @Id
    private String productNumber;
    private String name;
    private double price;
    private String description;
    private int numberInStock;

}
