package ru.bul.springs.SecApp.util;

import ru.bul.springs.SecApp.models.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getPrice()-o1.getPrice();
    }
}
