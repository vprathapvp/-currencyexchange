package com.example.currency.exchange.and.discount.calculation.config;

public class DiscountConstant {

    public static final double EMPLOYEE_DISCOUNT_RATE = 0.30; 
    public static final double AFFILIATE_DISCOUNT_RATE = 0.10; 
    public static final double LONG_TERM_CUSTOMER_DISCOUNT_RATE = 0.05;
    public static final int LONG_TERM_CUSTOMER_TENURE = 2;
    public static final int VALUE_ONE = 1;
    public static final int FLAT_DISCOUNT_RATE = 5; 
    public static final int VALUE_HUNDRED = 100; 
    public static final double VALUE_ZERO = 0.0;

    private DiscountConstant() {
        throw new UnsupportedOperationException("Cannot instantiate a utility class.");
    }
}
