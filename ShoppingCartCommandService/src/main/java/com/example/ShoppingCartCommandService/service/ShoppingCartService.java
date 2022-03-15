package com.example.ShoppingCartCommandService.service;

import com.example.ShoppingCartCommandService.dao.ShoppingCartDAO;
import com.example.ShoppingCartCommandService.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate2;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate3;

    @Autowired
    private ModelMapper modelMapper;

    public void createShoppingCart(Customer customer) throws JsonProcessingException {
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setCustomerId(customer.getCustomerId());
        shoppingCart.setShoppingCartNumber(customer.getCartNumber());
        shoppingCartDAO.save(shoppingCart);

        ShoppingCartDto shoppingCartDto = modelMapper.map(shoppingCart,ShoppingCartDto.class);
        System.out.println(shoppingCartDto);

        String cartDtoString = objectMapper.writeValueAsString(shoppingCartDto);
        String cartString = objectMapper.writeValueAsString(shoppingCart);
        kafkaTemplate.send("1",cartString);
        kafkaTemplate2.send("2", cartDtoString);
    }
    public ShoppingCart getShoppingCart(Long cartNumber){
        return shoppingCartDAO.findById(cartNumber).get();
    }
    public void updateShoppingCart(ShoppingCart cart){
        shoppingCartDAO.save(cart);
    }

    public void addOrRemoveProduct(Long cartNumber, Product product) throws JsonProcessingException {
       ShoppingCart cart = getShoppingCart(cartNumber);
       if(cart.getCartLines().containsKey(product.getProductNumber()))
           cart.getCartLines().remove(product.getProductNumber());
       else{
           cart.getCartLines().put(product.getProductNumber(),product);
       }
       ProductDto productDto = modelMapper.map(product,ProductDto.class);
       productDto.setCartNumber(cartNumber);
       String productDtoString = objectMapper.writeValueAsString(productDto);
       kafkaTemplate3.send("3",productDtoString);
       updateShoppingCart(cart);
    }

    public void deleteCart(Long cartNumber){
        shoppingCartDAO.deleteById(cartNumber);
    }
    public void checkoutCart(Long cartNumber) throws JsonProcessingException {
        ShoppingCart shoppingCart = shoppingCartDAO.findById(cartNumber).get();
        String cartString = objectMapper.writeValueAsString(shoppingCart);
        kafkaTemplate.send("4",cartString);
        shoppingCartDAO.deleteById(cartNumber);
    }
}
