package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product{
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) throws IllegalArgumentException{
        if (name == null || name.equals("") || price == null || tax == null || price.doubleValue() < 0){
            throw new IllegalArgumentException("Name, price and tax cannot be null. Name cannot be empty, price cannot be negative.");
        }

        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public BigDecimal getPriceWithTax() {

        return this.price.add(this.price.multiply(this.taxPercent));
    }
}
