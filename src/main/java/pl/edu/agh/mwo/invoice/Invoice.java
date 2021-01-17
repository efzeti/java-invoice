package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.Collection;

public class Invoice {
    private Collection<Product> products;

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    public BigDecimal getSubtotal() {
        return products
                .stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);   // map.reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTax() {
        return null;
    }

    public BigDecimal getTotal() {
        return products
                .stream()
                .map(x -> x.getPrice().add(x.getPrice().multiply(x.getTaxPercent())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
