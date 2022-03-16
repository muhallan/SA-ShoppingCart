package order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCart {
    @JsonProperty
    private String shopingCartNumber;
    @JsonProperty
    private String customerId;
    @JsonProperty
	private List<Product> cartLines;

    public String getShopingCartNumber() {
        return shopingCartNumber;
    }

    public void setShopingCartNumber(String shopingCartNumber) {
        this.shopingCartNumber = shopingCartNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Product> getCartLines() {
        return cartLines;
    }

    public void setCartLines(List<Product> cartLines) {
        this.cartLines = cartLines;
    }

}
