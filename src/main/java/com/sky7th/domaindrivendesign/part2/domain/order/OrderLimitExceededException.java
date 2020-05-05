package com.sky7th.domaindrivendesign.part2.domain.order;

class OrderLimitExceededException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "해당 사용자 제한 금액인 %s 보다 클 수 없습니다.";

    OrderLimitExceededException(final String limitPrice) {
        super(String.format(ERROR_MESSAGE, limitPrice));
    }
}
