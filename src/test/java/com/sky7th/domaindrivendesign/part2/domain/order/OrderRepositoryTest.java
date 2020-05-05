package com.sky7th.domaindrivendesign.part2.domain.order;

import com.sky7th.domaindrivendesign.part2.Registrar;
import com.sky7th.domaindrivendesign.part2.domain.customer.Customer;
import com.sky7th.domaindrivendesign.part2.domain.product.Product;
import com.sky7th.domaindrivendesign.part2.domain.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private Customer customer;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Registrar.init();
        orderRepository = new OrderRepository();
        productRepository = new ProductRepository();
        productRepository.save(new Product("상품1", 1000));
        productRepository.save(new Product("상품2", 5000));
        customer = new Customer("CUST-01", "홍길동", "경기도 안양시", 200000);
    }

    @DisplayName("특정 고객이 주문한 상품 수를 찾는다.")
    @Test
    void testOrdreCount() {
        orderRepository.save(customer.newOrder("CUST-01-ORDER-01")
                .with("상품1", 5)
                .with("상품2", 20)
                .with("상품1", 5));
        orderRepository.save(customer.newOrder("CUST-01-ORDER-02")
                .with("상품1", 20)
                .with("상품2", 5));
        assertEquals(2, orderRepository.findByCustomer(customer).size());

    }
}
