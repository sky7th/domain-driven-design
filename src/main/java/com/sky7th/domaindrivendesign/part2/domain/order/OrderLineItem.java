package com.sky7th.domaindrivendesign.part2.domain.order;

import com.sky7th.domaindrivendesign.part2.Money;
import com.sky7th.domaindrivendesign.part2.domain.product.Product;
import com.sky7th.domaindrivendesign.part2.domain.product.ProductRepository;

public class OrderLineItem {

    private Product product;
    private int quantity;

    OrderLineItem(String productName, int quantity) {
        ProductRepository productRepository = new ProductRepository();
        this.product = productRepository.find(productName);
        this.quantity = quantity;
    }

    Money getPrice() {
        return product.getPrice().multiply(quantity);
    }

    public Product getProduct() {
        return product;
    }

    boolean isProductEqual(OrderLineItem lineItem) {
        return product == lineItem.product;
    }

    OrderLineItem merge(OrderLineItem lineItem) {
        quantity += lineItem.quantity;
        return this;
    }
}