package com.sky7th.domaindrivendesign.part2.domain.customer;

import com.sky7th.domaindrivendesign.part2.EntryPoint;
import com.sky7th.domaindrivendesign.part2.Money;
import com.sky7th.domaindrivendesign.part2.domain.order.Order;

public class Customer extends EntryPoint {

    private String customerNumber;
    private String name;
    private String address;
    private Money mileage;
    private Money limitPrice;

    public Customer(String customerNumber, String name, String address,
                    long limitPrice) {

        super(customerNumber);
        this.customerNumber = customerNumber;
        this.name = name;
        this.address = address;
        this.limitPrice = new Money(limitPrice);
    }

    public Order newOrder(String orderId) {
        return Order.order(orderId, this);
    }

    public boolean isExceedLimitPrice(Money money) {
        return money.isGreaterThan(limitPrice);
    }

    public Money getLimitPrice() {
        return this.limitPrice;
    }
}