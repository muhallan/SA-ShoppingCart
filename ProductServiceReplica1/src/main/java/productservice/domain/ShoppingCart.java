package productservice.domain;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShoppingCart {
    String shoppingCartNumber;
    List<CartLine> products;
}
