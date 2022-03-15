package com.example.shoppingcartqueryservice.service;

import com.example.shoppingcartqueryservice.domain.Product;
import com.example.shoppingcartqueryservice.domain.ProductDto;
import com.example.shoppingcartqueryservice.domain.ShoppingCart;
import com.example.shoppingcartqueryservice.dao.ShoppingCartDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartQueryService {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    @Autowired
    private ModelMapper modelMapper;

    public ShoppingCart getShoppingCart(Long cartNumber){
        return shoppingCartDAO.findById(cartNumber).get();
    }
    public List<ShoppingCart> getShoppingCarts(){
        return shoppingCartDAO.findAll();
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
    public void updateShoppingCart(ShoppingCart cart){
        shoppingCartDAO.save(cart);
    }
    @KafkaListener(topics = {"1"})
    public void receiveOrder(@Payload String cartString) throws JsonProcessingException {
        ShoppingCart shoppingCart = objectMapper.readValue(cartString,ShoppingCart.class);
        shoppingCartDAO.save(shoppingCart);
    }
    @KafkaListener(topics = {"3"})
    public void updateProductInCart(@Payload String productDtoString) throws JsonProcessingException {
        ProductDto productDto = objectMapper.readValue(productDtoString,ProductDto.class);
        Product product = modelMapper.map(productDto,Product.class);
        addOrRemoveProduct(productDto.getCartNumber(),product);
    }
}
