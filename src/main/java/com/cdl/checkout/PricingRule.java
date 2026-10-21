package com.cdl.checkout;

public interface PricingRule {
    long priceFor(int count);
}
