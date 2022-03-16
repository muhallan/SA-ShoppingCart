package com.example.ShoppingCartCommandService.controller;

import com.example.ShoppingCartCommandService.domain.Customer;
import com.example.ShoppingCartCommandService.domain.Product;
import com.example.ShoppingCartCommandService.domain.Quantity;
import com.example.ShoppingCartCommandService.service.ShoppingCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody Customer customer) throws JsonProcessingException {
        if(shoppingCartService.createShoppingCart(customer))
            return new ResponseEntity<>("SUCCESSFULLY CREATED CART", HttpStatus.OK);
        else
            return new ResponseEntity<String>("CART NOT CREATED", HttpStatus.BAD_REQUEST);

    }
    @PutMapping("/{cartId}/products/{productNumber}")
    public ResponseEntity<String> updateCartQuantity(@RequestBody Quantity quantity, @PathVariable Long cartId,
                                   @PathVariable String productNumber) throws JsonProcessingException {

        if(shoppingCartService.changeQuantity(quantity,cartId,productNumber))
            return new ResponseEntity<>("SUCCESSFULLY UPDATED QUANTITY", HttpStatus.OK);
        else
            return new ResponseEntity<String>("QUANTITY NOT UPDATED", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/{cartId}/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @PathVariable Long cartId) throws JsonProcessingException {

        if(shoppingCartService.addProduct(cartId,product))
            return new ResponseEntity<>("SUCCESSFULLY ADDED PRODUCT", HttpStatus.OK);
        else
            return new ResponseEntity<String>("PRODUCT NOT ADDED", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{cartId}/products/{productNumber}")
    public ResponseEntity<String> removeProduct(@PathVariable Long cartId, @PathVariable String productNumber) throws JsonProcessingException {
        System.out.println("cardId " + cartId + " pNo " + productNumber);
        if(shoppingCartService.removeProduct(cartId,productNumber))
            return new ResponseEntity<>("SUCCESSFULLY REMOVED PRODUCT", HttpStatus.OK);
        else
            return new ResponseEntity<String>("PRODUCT NOT REMOVED", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{cartId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable Long cartId) throws JsonProcessingException {

        if(shoppingCartService.checkoutCart(cartId))
            return new ResponseEntity<>("CHECKOUT SUCCESS", HttpStatus.OK);
        else
            return new ResponseEntity<String>("CART NOT CHECKEDOUT", HttpStatus.BAD_REQUEST);
    }


}
