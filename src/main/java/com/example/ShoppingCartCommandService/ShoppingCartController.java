package com.example.ShoppingCartCommandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public void createCart(@RequestBody Long customerId){
        shoppingCartService.createShoppingCart(customerId);
    }

    @PutMapping("{/cartId}")
    public void addProduct(@PathVariable Long cartId, @RequestBody ProductDto productDto){
        shoppingCartService.addProduct(cartId,productDto);
    }
    @PutMapping("{/cartId}")
    public void removeProduct(@PathVariable Long cartId, @RequestBody ProductDto productDto){
        shoppingCartService.addProduct(cartId,productDto);
    }
}
