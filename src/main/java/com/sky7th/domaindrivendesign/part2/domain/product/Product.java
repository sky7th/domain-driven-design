package com.sky7th.domaindrivendesign.part2.domain.product;

import com.sky7th.domaindrivendesign.part2.EntryPoint;
import com.sky7th.domaindrivendesign.part2.Money;

public class Product extends EntryPoint {

    private Money price;
    private String name;

    public Product(String name, long price) {
        super(name);
        this.price = new Money(price);
    }

    public Product(String name, Money price) {
        super(name);
        this.price = price;
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}