package com.example.ShoppingCartCommandService.controller;

import com.example.ShoppingCartCommandService.domain.Customer;
import com.example.ShoppingCartCommandService.domain.Product;
import com.example.ShoppingCartCommandService.service.ShoppingCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public void createCart(@RequestBody Customer customer) throws JsonProcessingException {
        shoppingCartService.createShoppingCart(customer);
    }
    @GetMapping
    public String testGet(){
        return "Check check check";
    }
    @PutMapping("/{cartId}/products")
    public void addOrRemoveProduct(@PathVariable Long cartId, @RequestBody Product productDto) throws JsonProcessingException {
        shoppingCartService.addOrRemoveProduct(cartId,productDto);
    }
    @GetMapping("/{cartId}/checkout")
    public void checkoutCart(@PathVariable Long cartId) throws JsonProcessingException {
        shoppingCartService.checkoutCart(cartId);
    }

}
