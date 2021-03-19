package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {
    private static Integer highestNumber;
    private int number;
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    List<String> productNameList = new ArrayList<>();


    public Invoice() {
        if (highestNumber == null){
            highestNumber = 1;
            number = highestNumber;
        } else {
            highestNumber++;
            number = highestNumber;
        }
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        Product existingProduct = null;
        for (Product mapProduct : products.keySet()){
            if (mapProduct.getName().equals(product.getName())){
                existingProduct = mapProduct;
            }
        }

        if (existingProduct != null){
            products.put(existingProduct, quantity + products.get(existingProduct));
        } else {
            products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber(){
        return number;
    }

    public int getItemsCount(){
        return products.size();
    }

    public int getTotalProducts(){
        int totalProducts = 0;
        for (Map.Entry<Product, Integer> product : products.entrySet()) {
            totalProducts += product.getValue();
        }

        return totalProducts;
    }

    public void printInvoice(){
        int itemPosition = 1;
        System.out.format("|---------------------------------|\n");
        System.out.format("|=====X Invoice No. %07d X=====|\n", number);
        System.out.println("|pos name       count     price   |");
        for (Map.Entry<Product, Integer> product : products.entrySet()) {
            System.out.format("|%03d %-10s %04d %10.2fPLN|\n", itemPosition++, product.getKey().getName(), product.getValue(), (double) product.getValue() * product.getKey().getPriceWithTax().doubleValue());
        }
        System.out.format("|                                 |\n");
        System.out.format("|TOTAL:         %04d %10.2fPLN|\n",getTotalProducts(),getGrossTotal());
        System.out.format("|TOTAL UNIQUE POSITIONS: %03d      |\n" ,getItemsCount());
        System.out.format("|---------------------------------|\n");


    }
}
