package com.example.ShoppingCartCommandService;

import com.example.ShoppingCartCommandService.domain.Customer;
import com.example.ShoppingCartCommandService.domain.Product;
import com.example.ShoppingCartCommandService.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    @Autowired
    private KafkaTemplate<String, ShoppingCart> kafkaTemplate;

    public void createShoppingCart(Customer customer){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(customer.getCustomerId());
        shoppingCart.setShopingCartNumber(customer.getCartNumber());
        shoppingCartDAO.save(shoppingCart);
        kafkaTemplate.send("order_created",shoppingCart);
    }
    public ShoppingCart getShoppingCart(Long cartNumber){
        return shoppingCartDAO.findById(cartNumber).get();
    }
    public void updateShoppingCart(ShoppingCart cart){
        shoppingCartDAO.save(cart);
    }

    public void addOrRemoveProduct(Long cartNumber, Product productDto){
       ShoppingCart cart = getShoppingCart(cartNumber);
       if(cart.getCartLines().containsKey(productDto.getProductNumber()))
           cart.getCartLines().remove(productDto.getProductNumber());
       else{
           cart.getCartLines().put(productDto.getProductNumber(),productDto);
       }
       updateShoppingCart(cart);
    }

    public void deleteCart(Long cartNumber){
        shoppingCartDAO.deleteById(cartNumber);
    }
    public void checkoutCart(Long cartNumber){
        kafkaTemplate.send("ordercreated",shoppingCartDAO.findById(cartNumber).get());
    }
}
