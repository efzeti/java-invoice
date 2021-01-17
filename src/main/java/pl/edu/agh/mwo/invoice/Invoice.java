package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Invoice {
    private Map<Product, Integer> products;

    public Invoice() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        if (products.get(product) != null){
            products.put(product, products.get(product) + 1);
        }
        products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity cannot be 0 or negative");
        }

            products.put(product, quantity);

    }

    public BigDecimal getNetPrice() {
        return products.entrySet()
                .stream()
                .map(x -> x.getKey().getPrice().multiply(BigDecimal.valueOf(x.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTax() {
        return getGrossPrice().subtract(getNetPrice());
    }

    public BigDecimal getGrossPrice() {
        return products.entrySet()
                .stream()
                .map(x -> x.getKey().getPriceWithTax().multiply(BigDecimal.valueOf(x.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
