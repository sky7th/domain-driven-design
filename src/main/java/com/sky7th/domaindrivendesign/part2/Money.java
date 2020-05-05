package com.sky7th.domaindrivendesign.part2;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money(long amount) {
        this(new BigDecimal(amount));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Money)) {
            return false;
        }
        return amount.equals(((Money)object).amount);
    }

    public boolean isGreaterThan(Money compared) {
        return this.amount.compareTo(compared.amount) > 0;
    }

    public int hashCode() {
        return amount.hashCode();
    }

    public Money add(Money added) {
        return new Money(this.amount.add(added.amount));
    }

    public Money multiply(Money multiplied) {
        return new Money(this.amount.multiply(multiplied.amount));
    }

    public Money multiply(int amount) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(amount)));
    }

    public String toString() {
        return amount.toString();
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
}