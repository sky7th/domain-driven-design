package com.sky7th.domaindrivendesign.part1;

import com.sky7th.domaindrivendesign.part1.Customer;
import com.sky7th.domaindrivendesign.part1.Registrar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CustomerTest {

    @BeforeEach
    void setUp() {
        Registrar.init();
    }

    @DisplayName("customer가 1,000원짜리 상품을 구매하여 마일리지리 1%를 적립한다.")
    @Test
    void testAliasing() {
        // given
        Customer customer = new Customer("CUST-01", "홍길동", "경기도 안양시");
        // when
        long price = 1000;
        customer.purchase(price);
        // then
        assertEquals(price * 0.01, customer.getMileage(), 0.1);
    }

    @DisplayName("Customer 객체를 조회한 후 반환된 anotherCustomer가 이미 등록된 Customer 클래스와 동일한 식별자를 가지고 있는 지 검사한다.")
    @Test
    void testCustomerIdentical() {
        // given
        CustomerRepository customerRepository = new CustomerRepository();
        Customer customer = new Customer("CUST-01", "홍길동", "경기도 안양시");
        customerRepository.save(customer);
        // when
        Customer anotherCustomer = customerRepository.find("CUST-01");
        // then
        assertSame(customer, anotherCustomer);
    }
}