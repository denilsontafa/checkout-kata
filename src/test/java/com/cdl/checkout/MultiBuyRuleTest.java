package com.cdl.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class MultiBuyRuleTest {
    private final PricingRule rule = new MultiBuyRule(50, 3, 130);

    @Test
    void threeItemsGetTheOfferPrice() {
        assertThat(rule.priceFor(3)).isEqualTo(130);
    }

    @Test
    void fourItemsGetTheOfferPricePlusOneUnitPrice() {
        assertThat(rule.priceFor(4)).isEqualTo(180);
    }

    @Test
    void sixItemsGetTwoOfferPrices() {
        assertThat(rule.priceFor(6)).isEqualTo(260);
    }

    @Test
    void twoItemsGetTheUnitPrice() {
        assertThat(rule.priceFor(2)).isEqualTo(100);
    }

    @Test
    void zeroItemsGetZeroPrice() {
        assertThat(rule.priceFor(0)).isZero();
    }

    @Test
    void quantityBelowTwoIsRejected() {
        assertThatThrownBy(() -> new MultiBuyRule(50, 1, 130))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("quantity");
    }

    @Test
    void unitPriceZeroIsRejected() {
        assertThatThrownBy(() -> new MultiBuyRule(0, 3, 130))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit price");
    }

    @Test
    void offerPriceZeroIsRejected() {
        assertThatThrownBy(() -> new MultiBuyRule(50, 3, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Offer price");
    }

    @Test
    void offerPriceNegativeIsRejected() {
        assertThatThrownBy(() -> new MultiBuyRule(50, 3, -10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Offer price");
    }
}
