package com.example.shoppingcartqueryservice.dao;

import com.example.shoppingcartqueryservice.domain.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDAO extends MongoRepository<ShoppingCart, Long> {
}
