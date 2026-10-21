package com.cdl.checkout;

public class UnitPriceRule implements PricingRule {

    private final long unitPrice;

    public UnitPriceRule(long unitPrice) {
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive but was " + unitPrice);
        }
        this.unitPrice = unitPrice;
    }

    @Override
    public long priceFor(int count) {
        return unitPrice * count;
    }
}