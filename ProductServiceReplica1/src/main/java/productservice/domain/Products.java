package productservice.domain;

import java.util.List;

public class Products {
    private List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public Products() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Products{" +
                "products=" + products +
                '}';
    }
}
