package order.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties
    private String shoppingCartNumber;
    @JsonProperty
    private String customerId;
    @JsonProperty
	private List<Product> products;

    public String getShopingCartNumber() {
        return shoppingCartNumber;
    }

    public void setShopingCartNumber(String shopingCartNumber) {
        this.shoppingCartNumber = shopingCartNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
