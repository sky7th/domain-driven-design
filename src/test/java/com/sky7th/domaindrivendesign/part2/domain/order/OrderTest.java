package com.sky7th.domaindrivendesign.part2.domain.order;

import com.sky7th.domaindrivendesign.part2.Money;
import com.sky7th.domaindrivendesign.part2.Registrar;
import com.sky7th.domaindrivendesign.part2.domain.customer.Customer;
import com.sky7th.domaindrivendesign.part2.domain.product.Product;
import com.sky7th.domaindrivendesign.part2.domain.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

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

    @DisplayName("주문 총액이 고객의 주문 한도액을 초과하면 안된다.")
    @Test
    void testOrderLimitExceed() {
        try {
            customer.newOrder("CUST-01-ORDER-01")
                    .with("상품1", 20)
                    .with("상품2", 50);
            fail();

        } catch(OrderLimitExceededException ex) {
            assertTrue(true);
        }
    }

    @DisplayName("주문 시에 동일한 상품을 두 번으로 나누어서 구매한다.")
    @Test
    void testOrderWithEqualProductsPrice() {
        Order order = customer.newOrder("CUST-01-ORDER-01")
                .with("상품1", 5)
                .with("상품2", 20)
                .with("상품1", 5);
        orderRepository.save(order);
        Assertions.assertEquals(new Money(110000), order.getPrice());
    }

    @DisplayName("주문한 상품 개수와 주문 항목의 개수가 같다.")
    @Test
    void testOrdreLineItems() {
        Order order = customer.newOrder("CUST-01-ORDER-01")
                .with("상품1", 5)
                .with("상품2", 20)
                .with("상품1", 5);
        orderRepository.save(order);
        assertEquals(2, order.getOrderLineItemSize());
    }
}