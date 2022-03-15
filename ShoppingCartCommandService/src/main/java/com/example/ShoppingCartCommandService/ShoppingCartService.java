package com.example.ShoppingCartCommandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    @Autowired
    private KafkaTemplate<String,ShoppingCart> kafkaTemplate;

    public void createShoppingCart(Long customerId){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(customerId);
        shoppingCartDAO.save(shoppingCart);
    }
    public ShoppingCart getShoppingCart(Long cartNumber){
        return shoppingCartDAO.findById(cartNumber).get();
    }
    public void updateShoppingCart(ShoppingCart cart){
        shoppingCartDAO.save(cart);
    }

    public void addProduct(Long cartNumber, ProductDto productDto){
       ShoppingCart cart = getShoppingCart(cartNumber);
       cart.getCartLines().put(productDto.getProductNumber(),productDto);
       updateShoppingCart(cart);
    }
    public void removeProduct(Long cartNumber, Long productNumber){
        ShoppingCart cart = getShoppingCart(cartNumber);
        cart.getCartLines().remove(productNumber);
        updateShoppingCart(cart);
    }
    public void deleteCart(Long cartNumber){
        shoppingCartDAO.deleteById(cartNumber);
    }
    public void checkoutCart(Long cartNumber){
        kafkaTemplate.send("ordercreated",shoppingCartDAO.findById(cartNumber).get());
    }
}
