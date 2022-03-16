package order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCart {
    public Long getShopingCartNumber() {
		return shopingCartNumber;
	}
	public void setShopingCartNumber(Long shopingCartNumber) {
		this.shopingCartNumber = shopingCartNumber;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public HashMap<Long, Product> getCartLines() {
		return cartLines;
	}
	public void setCartLines(HashMap<Long, Product> cartLines) {
		this.cartLines = cartLines;
	}
	
	@JsonProperty
	private Long shopingCartNumber;
	@JsonProperty
    private Long customerId;
	@JsonProperty
    private HashMap<Long, Product> cartLines = new HashMap<>();
}
