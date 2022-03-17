package com.example.shoppingcartqueryservice.service;

import com.example.shoppingcartqueryservice.domain.Product;
import com.example.shoppingcartqueryservice.domain.ProductDto;
import com.example.shoppingcartqueryservice.domain.ShoppingCart;
import com.example.shoppingcartqueryservice.dao.ShoppingCartDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(CartQueryService.class);

    public ShoppingCart getShoppingCart(Long cartNumber){
        return shoppingCartDAO.findById(cartNumber).get();
    }

    public List<ShoppingCart> getShoppingCarts(){
        return shoppingCartDAO.findAll();
    }

    public void updateShoppingCart(ShoppingCart cart){
        shoppingCartDAO.save(cart);
    }

    @KafkaListener(topics = {"CART-CREATED"})
    public void createCart(@Payload String cartString) throws JsonProcessingException {
        log.info("Received Kafka message on topic: CART-CREATED with payload: " + cartString);
        ShoppingCart newCart = objectMapper.readValue(cartString,ShoppingCart.class);
        shoppingCartDAO.save(newCart);
    }

    @KafkaListener(topics = {"CHECKOUT-FOR-QUERY"})
    public void checkOut(@Payload Long cartNumber)  {
        log.info("Received Kafka message on topic: CHECKOUT-FOR-QUERY with payload: " + cartNumber);
       shoppingCartDAO.deleteById(cartNumber);
    }

    @KafkaListener(topics = {"REMOVE-PRODUCT"})
    public void removeProduct(@Payload String productDtoString ) throws  JsonProcessingException{
        log.info("Received Kafka message on topic: REMOVE-PRODUCT with payload: " + productDtoString);
        ProductDto productDto = objectMapper.readValue(productDtoString,ProductDto.class);
        Product product = modelMapper.map(productDto,Product.class);
        ShoppingCart cart = getShoppingCart(productDto.getCartNumber());
        cart.getCartLines().remove(product.getProductNumber());
        shoppingCartDAO.save(cart);
    }

    @KafkaListener(topics = {"ADD-PRODUCT"})
    public void addProduct(@Payload String productDtoString ) throws  JsonProcessingException{
        log.info("Received Kafka message on topic: ADD-PRODUCT with payload: " + productDtoString);
        ProductDto productDto = objectMapper.readValue(productDtoString,ProductDto.class);
        Product product = modelMapper.map(productDto,Product.class);
        ShoppingCart cart = getShoppingCart(productDto.getCartNumber());
        cart.getCartLines().put(product.getProductNumber(),product);
        shoppingCartDAO.save(cart);
    }

    @KafkaListener(topics = {"CHANGE-QUANTITY"})
    public  void changeQuantity(@Payload String productDtoString) throws JsonProcessingException {
        log.info("Received Kafka message on topic: CHANGE-QUANTITY with payload: " + productDtoString);
        ProductDto productDto = objectMapper.readValue(productDtoString,ProductDto.class);
        Product product = modelMapper.map(productDto,Product.class);
        ShoppingCart cart = getShoppingCart(productDto.getCartNumber());
        cart.getCartLines().get(product.getProductNumber()).setQuantity(product.getQuantity());
        shoppingCartDAO.save(cart);
    }
}
