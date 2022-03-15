package com.example.ShoppingCartCommandService;

import com.example.ShoppingCartCommandService.domain.Customer;
import com.example.ShoppingCartCommandService.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public void createCart(@RequestBody Customer customer){
        shoppingCartService.createShoppingCart(customer);
    }
    @GetMapping
    public String testGet(){
        return "Check check check";
    }
    @PutMapping("/{cartId}/products")
    public void addOrRemoveProduct(@PathVariable Long cartId, @RequestBody Product productDto){
        shoppingCartService.addOrRemoveProduct(cartId,productDto);
    }

}
