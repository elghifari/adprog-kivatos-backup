package com.service.kivatos.utils.enumeration;

import java.math.BigDecimal;

public enum AdminFeeType {
    DANI(new BigDecimal("2500")),
    GAPAY(new BigDecimal("3000")),
    OVI(new BigDecimal("3500")),
    SHOPOOPAY(new BigDecimal("4000"));

    private final BigDecimal price;

    AdminFeeType(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
