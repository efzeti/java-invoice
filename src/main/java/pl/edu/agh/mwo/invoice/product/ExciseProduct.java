package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class ExciseProduct extends Product{

    private double excise = 5.56;

    public ExciseProduct(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(BigDecimal.valueOf(excise));
    }
}
