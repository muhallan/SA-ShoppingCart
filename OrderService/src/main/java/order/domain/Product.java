package order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
	@JsonIgnoreProperties
    private Long productNumber;
	@JsonIgnoreProperties
    private int quantity;
	@JsonIgnoreProperties
    private double price;
}
