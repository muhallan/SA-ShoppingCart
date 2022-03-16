package order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCart {
	@JsonProperty
	private Long shopingCartNumber;
	@JsonProperty
    private Long customerId;
	@JsonProperty
    private List<Product> cartLines;
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

	public List<Product> getCartLines() {
		return cartLines;
	}

	public void setCartLines(List<Product> cartLines) {
		this.cartLines = cartLines;
	}

}
