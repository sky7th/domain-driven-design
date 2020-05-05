package com.sky7th.domaindrivendesign.part2.domain.product;

import com.sky7th.domaindrivendesign.part2.Registrar;

public class ProductRepository {

    public void save(Product product) {
        Registrar.add(Product.class, product);
    }

    public Product find(String identity) {
        return (Product) Registrar.get(Product.class, identity);
    }
}