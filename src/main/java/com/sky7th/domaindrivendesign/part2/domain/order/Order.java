package com.sky7th.domaindrivendesign.part2.domain.order;

import com.sky7th.domaindrivendesign.part2.EntryPoint;
import com.sky7th.domaindrivendesign.part2.Money;
import com.sky7th.domaindrivendesign.part2.domain.customer.Customer;

import java.util.HashSet;
import java.util.Set;

public class Order extends EntryPoint {

    private Set<OrderLineItem> lineItems = new HashSet<>();
    private Customer customer;

    public static Order order(String orderId, Customer customer) {
        return new Order(orderId, customer);
    }

    private Order(String orderId, Customer customer) {
        super(orderId);
        this.customer = customer;
    }

    public Order with(String productName, int quantity) throws OrderLimitExceededException {
        return with(new OrderLineItem(productName, quantity));
    }

    private Order with(OrderLineItem lineItem) throws OrderLimitExceededException {
        if (isExceedLimit(customer, lineItem)) {
            throw new OrderLimitExceededException(customer.getLimitPrice().toString());
        }

        for(OrderLineItem item : lineItems) {
            if (item.isProductEqual(lineItem)) {
                item.merge(lineItem);
                return this;
            }
        }

        lineItems.add(lineItem);
        return this;

    }

    private boolean isExceedLimit(Customer customer, OrderLineItem lineItem) {
        return customer.isExceedLimitPrice(getPrice().add(lineItem.getPrice()));

    }

    public Money getPrice() {
        Money result = new Money(0);

        for(OrderLineItem item : lineItems) {
            result = result.add(item.getPrice());
        }
        return result;
    }

    public int getOrderLineItemSize() {
        return lineItems.size();
    }

    boolean isOrderedBy(Customer customer) {
        return this.customer == customer;
    }
}