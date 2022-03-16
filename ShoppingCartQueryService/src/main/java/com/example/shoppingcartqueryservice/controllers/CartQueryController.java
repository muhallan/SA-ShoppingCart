package com.example.shoppingcartqueryservice.controllers;

import com.example.shoppingcartqueryservice.domain.ShoppingCart;
import com.example.shoppingcartqueryservice.service.CartQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getCart(@PathVariable Long cartNumber){
        if(cartQueryService.getShoppingCart(cartNumber) !=null){
            return new ResponseEntity<ShoppingCart>(cartQueryService.getShoppingCart(cartNumber), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("COULDN'T GET CART", HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping()
    public ResponseEntity<?> getCart(){
        if(cartQueryService.getShoppingCarts() !=null){
            return new ResponseEntity<List<ShoppingCart>>(cartQueryService.getShoppingCarts(), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("COULDN'T GET CARTS", HttpStatus.BAD_REQUEST);
        }

    }

}
