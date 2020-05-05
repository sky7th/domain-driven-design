package com.sky7th.domaindrivendesign.part2.domain.customer;

import com.sky7th.domaindrivendesign.part2.Registrar;

public class CustomerRepository {

    public void save(Customer customer) {
        Registrar.add(Customer.class, customer);
    }

    public Customer find(String identity) {
        return (Customer) Registrar.get(Customer.class, identity);
    }
}