package com.cdl.checkout;

import java.util.Map;

/** The current price list. Change prices here, nowhere else. */
public final class DefaultPricing {

    private DefaultPricing() {
    }

    public static Map<Character, PricingRule> rules() {
        return Map.of(
                'A', new MultiBuyRule(50, 3, 130),
                'B', new MultiBuyRule(30, 2, 45),
                'C', new UnitPriceRule(20),
                'D', new UnitPriceRule(15));
    }
}