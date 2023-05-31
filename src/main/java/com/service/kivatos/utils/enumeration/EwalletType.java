package com.service.kivatos.utils.enumeration;

import java.math.BigDecimal;

public enum EwalletType {
    DANI("dani", AdminFeeType.DANI.getPrice()),
    GAPAY("gapay", AdminFeeType.GAPAY.getPrice()),
    OVI("ovi", AdminFeeType.OVI.getPrice()),
    SHOPOOPAY("shopoopay", AdminFeeType.SHOPOOPAY.getPrice());

    private final String ewalletType;
    private final BigDecimal price;

    EwalletType(String ewalletType, BigDecimal price) {
        this.ewalletType = ewalletType;
        this.price = price;
    }

    public static EwalletType fromString(String ewalletType) {
        for (EwalletType type : EwalletType.values()) {
            if (type.ewalletType.equalsIgnoreCase(ewalletType)) {
                return type;
            }
        }
        return null;
    }

    public String getEwalletType() {
        return ewalletType;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

