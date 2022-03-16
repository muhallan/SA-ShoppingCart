package com.example.ShoppingCartCommandService.service;

import com.example.ShoppingCartCommandService.dao.ShoppingCartDAO;
import com.example.ShoppingCartCommandService.domain.*;
import com.example.ShoppingCartCommandService.dto.ProductDto;
import com.example.ShoppingCartCommandService.dto.ProductForOrderDto;
import com.example.ShoppingCartCommandService.dto.ProductForProductDto;
import com.example.ShoppingCartCommandService.dto.ShoppingCartDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ShoppingCartService {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ShoppingCartDAO shoppingCartDAO;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Long> kafkaTemplate2;



    @Autowired
    private ModelMapper modelMapper;

    public boolean createShoppingCart(Customer customer) throws JsonProcessingException {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(customer.getCustomerId());
        shoppingCart.setShoppingCartNumber(customer.getCartNumber());
        shoppingCartDAO.save(shoppingCart);
        ShoppingCartDto shoppingCartDto = modelMapper.map(shoppingCart,ShoppingCartDto.class);
        String cartDtoString = objectMapper.writeValueAsString(shoppingCartDto);
        String cartString = objectMapper.writeValueAsString(shoppingCart);
        kafkaTemplate.send("CART-CREATED",cartString);
        return  true;

    }
    public ShoppingCart getShoppingCart(Long cartNumber){
        return shoppingCartDAO.findById(cartNumber).get();
    }


    public void updateShoppingCart(ShoppingCart cart){
        shoppingCartDAO.save(cart);
    }

    public boolean changeQuantity(Quantity quantity, Long cartId, Long productId) throws JsonProcessingException {
        if(shoppingCartDAO.existsById(cartId)){
            ShoppingCart cart = shoppingCartDAO.findById(cartId).get();
            Product product = cart.getCartLines().get(productId);
            if(product!=null){
                product.setQuantity(quantity.getQuantity());
                updateShoppingCart(cart);
                ProductDto productDto = modelMapper.map(product,ProductDto.class);
                productDto.setCartNumber(cartId);
                String productDtoString = objectMapper.writeValueAsString(productDto);
                kafkaTemplate.send("CHANGE-QUANTITY", productDtoString);
                return true;
            }
        }
        return false;
    }
    public boolean addProduct( Long cartId, Product product) throws JsonProcessingException {
        if(shoppingCartDAO.existsById(cartId)){
            ShoppingCart cart = shoppingCartDAO.findById(cartId).get();
            Product productCheck = cart.getCartLines().get(product.getProductNumber());
            if(productCheck == null){
                cart.getCartLines().put(product.getProductNumber(),product);
                updateShoppingCart(cart);
                ProductDto productDto = modelMapper.map(product,ProductDto.class);
                productDto.setCartNumber(cartId);
                String productDtoString = objectMapper.writeValueAsString(productDto);
                System.out.println(productDtoString);
                kafkaTemplate.send("ADD-PRODUCT", productDtoString);
                return true;
            }
        }
        return false;
    }
    public boolean removeProduct(Long cartId, Long productId) throws JsonProcessingException {
        if(shoppingCartDAO.existsById(cartId)){
            ShoppingCart cart = shoppingCartDAO.findById(cartId).get();
            Product product = cart.getCartLines().get(productId);
            if(product!=null){
                cart.getCartLines().remove(productId);
                updateShoppingCart(cart);
                ProductDto productDto = modelMapper.map(product,ProductDto.class);
                productDto.setCartNumber(cartId);
                String productDtoString = objectMapper.writeValueAsString(productDto);
                kafkaTemplate.send("REMOVE-PRODUCT", productDtoString);
                return true;
            }
        }
        return false;
    }


    public boolean deleteCart(Long cartNumber) throws JsonProcessingException {
        if(shoppingCartDAO.existsById(cartNumber)){
            shoppingCartDAO.deleteById(cartNumber);
            String cartString = objectMapper.writeValueAsString(shoppingCartDAO.findById(cartNumber));
            kafkaTemplate.send("DELETE-CART",cartString);
            return true;
        }else{
            return false;
        }
    }
    public boolean checkoutCart(Long cartNumber) throws JsonProcessingException {
        if(shoppingCartDAO.existsById(cartNumber)){
            ShoppingCart cart = shoppingCartDAO.findById(cartNumber).get();
            //String cartString = objectMapper.writeValueAsString(cart);
            List<Product> products = convertToList(cart.getCartLines());
            shoppingCartDAO.deleteById(cartNumber);
            sendCheckOutMessage(products,cart.getCustomerId());
            kafkaTemplate2.send("CHECKOUT-FOR-QUERY",cart.getShoppingCartNumber());
        }
        return true;

    }

    public List<Product> convertToList(HashMap<Long, Product> productHashMap){
        List<Product> products =new ArrayList<>(productHashMap.values());
        return products;
    }
    public void sendCheckOutMessage(List<Product> products, String customerId) throws JsonProcessingException {
        ProductForOrderDto productForOrderDto = new ProductForOrderDto();
        productForOrderDto.setProducts(products);
        productForOrderDto.setCustomerId(customerId);
        String productOrderString = objectMapper.writeValueAsString(productForOrderDto);
        kafkaTemplate.send("CHECKOUT-FOR-ORDER",productOrderString);

        ProductForProductDto productForProductDto = new ProductForProductDto();
        productForProductDto.setProducts(products);
        String productStockString = objectMapper.writeValueAsString(productForProductDto);
        kafkaTemplate.send("CHECKOUT-FOR-PRODUCT",productStockString);
    }
}
