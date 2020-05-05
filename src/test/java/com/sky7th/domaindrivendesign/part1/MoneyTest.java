package com.sky7th.domaindrivendesign.part1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @DisplayName("doSomethingWithMoney 메소드가 종료된 후 금액이 변경되지 않는다.")
    @Test
    void testMethodAlaising() {
        // given
        Money money = new Money(2000);
        // when
        doSomethingWithMoney(money);
        // then
        assertEquals(new Money(2000), money);
    }

    void doSomethingWithMoney(final Money money) {
        money.add(new Money(2000));
    }
}