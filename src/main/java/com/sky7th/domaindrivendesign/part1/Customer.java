package com.sky7th.domaindrivendesign.part1;

public class Customer extends EntryPoint {
    private String customerNumber;
    private String name;
    private String address;
    private long mileage;

    public Customer(String customerNumber, String name, String address) {
        super(customerNumber);
        this.customerNumber = customerNumber;
        this.name = name;
        this.address = address;
    }

    public void purchase(long price) {
        mileage += price * 0.01;
    }

    public boolean isPossibleToPayWithMileage(long price) {
        return mileage > price;
    }

    public boolean payWithMileage(long price) {
        if (!isPossibleToPayWithMileage(price)) {
            return false;
        }
        mileage -= price;

        return true;
    }

    public long getMileage() {
        return mileage;
    }
}