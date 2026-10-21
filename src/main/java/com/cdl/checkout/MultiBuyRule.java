package com.cdl.checkout;

public class MultiBuyRule implements PricingRule {

    private final long unitPrice;
    private final int quantity;
    private final long offerPrice;

    public MultiBuyRule(long unitPrice, int quantity, long offerPrice) {
        if (quantity < 2) {
            throw new IllegalArgumentException("quantity must be at least 2");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be positive but was " + unitPrice);
        }
        if (offerPrice <= 0) {
            throw new IllegalArgumentException("Offer price must be positive but was " + offerPrice);
        }
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.offerPrice = offerPrice;
    }

    @Override
    public long priceFor(int count) {
        int fullGroups = count / quantity;
        int leftOver = count % quantity;

        return fullGroups * offerPrice + leftOver * unitPrice;
    }
}
