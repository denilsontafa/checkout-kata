package com.cdl.checkout;

import java.util.HashMap;
import java.util.Map;

/**
 * One checkout transaction. Prices are passed in, never hardcoded here.
 * Money is whole pence (long) so there are no floating-point errors.
 */
public class Checkout {

    private final Map<Character, PricingRule> rules;
    private final Map<Character, Integer> counts = new HashMap<>();

    public Checkout(Map<Character, PricingRule> rules) {
        this.rules = Map.copyOf(rules);
    }

    public void scan(char sku) {
        if (!rules.containsKey(sku)) {
            throw new IllegalArgumentException("Unknown item: " + sku);
        }
        counts.merge(sku, 1, Integer::sum);
    }

    /** Total so far, in pence. Works as the running total after each scan. */
    public long total() {
        long total = 0;
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            PricingRule rule = rules.get(entry.getKey());
            total += rule.priceFor(entry.getValue());
        }
        return total;
    }
}
