package com.example.shoppingcartqueryservice.controllers;

import com.example.shoppingcartqueryservice.domain.ShoppingCart;
import com.example.shoppingcartqueryservice.service.CartQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartQueryController {

    @Autowired
    private CartQueryService cartQueryService;

    @GetMapping("/{cartNumber}")
    public ShoppingCart getCart(@PathVariable Long cartNumber){
        return cartQueryService.getShoppingCart(cartNumber);
    }
    @GetMapping
    public List<ShoppingCart> getCarts(){
        return cartQueryService.getShoppingCarts();
    }
}
