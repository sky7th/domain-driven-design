package com.sky7th.domaindrivendesign.part2.domain.order;

import com.sky7th.domaindrivendesign.part2.Registrar;
import com.sky7th.domaindrivendesign.part2.domain.customer.Customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OrderRepository {

    public void save(Order customer) {
        Registrar.add(Order.class, customer);
    }

    public Order find(String identity) {
        return (Order) Registrar.get(Order.class, identity);
    }

    public Set<Order> findByCustomer(Customer customer) {
        Set<Order> results = new HashSet<>();

        for(Order order : findAll()) {
            if (order.isOrderedBy(customer)) {
                results.add(order);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private Set<Order> findAll() {
        return new HashSet<>((Collection<Order>) Registrar.getAll(Order.class));
    }
}