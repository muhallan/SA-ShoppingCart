package productservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productservice.domain.Product;
import productservice.domain.Products;
import productservice.domain.Quantity;
import productservice.service.OrderCreatedService;
import productservice.service.ProductService;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedService.class);

    @GetMapping("/products/{productNumber}")
    public ResponseEntity<?> getProductByNumber(@PathVariable String productNumber) {
        log.info("GET request for /products/" + productNumber);
        Product product = productService.findProductByNumber(productNumber);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        log.info("POST request for /products/ with body: " + product);
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        log.info("GET request for /products/");
        Products allProducts = new Products(productService.findAll());
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productNumber}")
    public ResponseEntity<?> deleteProductByNumber(@PathVariable String productNumber) {
        log.info("DELETE request for /products/" + productNumber);
        Product product = productService.findProductByNumber(productNumber);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/{productNumber}")
    public ResponseEntity<?> updateProductByNumber(@PathVariable String productNumber, @RequestBody Product product) {
        log.info("PUT request for /products/" + productNumber + " with body: " + product);
        Product oldProduct = productService.findProductByNumber(productNumber);
        if (oldProduct == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products/{productNumber}/quantity")
    public ResponseEntity<?> getProductQuantityByNumber(@PathVariable String productNumber) {
        log.info("GET request for /products/" + productNumber + "/quantity");
        Product product = productService.findProductByNumber(productNumber);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.getNumberInStock(), HttpStatus.OK);
    }

    @PutMapping("/products/{productNumber}/deduct_stock")
    public ResponseEntity<?> updateProductQuantityByNumber(@PathVariable String productNumber, @RequestBody Quantity quantity) {
        log.info("PUT request for /products/" + productNumber + "/deduct_stock with body: " + quantity);
        Product oldProduct = productService.findProductByNumber(productNumber);
        if (oldProduct == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        oldProduct.setNumberInStock(oldProduct.getNumberInStock() - quantity.getQuantity());
        log.info("Reducing the number in stock of product: " + oldProduct.getProductNumber() + " to " + oldProduct.getNumberInStock());
        productService.addProduct(oldProduct);
        return new ResponseEntity<>(oldProduct, HttpStatus.OK);
    }
}
