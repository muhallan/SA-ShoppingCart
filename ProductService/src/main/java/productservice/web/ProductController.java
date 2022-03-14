package productservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import productservice.domain.Product;
import productservice.domain.Products;
import productservice.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productNumber}")
    public ResponseEntity<?> getProductByNumber(@PathVariable String productNumber) {
        Product product = productService.findProductByNumber(productNumber);
        if (product == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        Products allProducts = new Products(productService.findAll());
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productNumber}")
    public ResponseEntity<?> deleteProductByNumber(@PathVariable String productNumber) {
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
        Product oldProduct = productService.findProductByNumber(productNumber);
        if (oldProduct == null) {
            return new ResponseEntity<>(new CustomErrorType("Product with productNumber= "
                    + productNumber + " was not found"), HttpStatus.NOT_FOUND);
        }
        productService.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
