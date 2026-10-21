package com.cdl.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class UnitPriceRuleTest {

    @Test
    void pricesEachItemAtTheUnitPrice() {
        PricingRule rule = new UnitPriceRule(20);
        assertThat(rule.priceFor(3)).isEqualTo(60);
    }

    @Test
    void zeroPriceIsRejected() {
        assertThatThrownBy(() -> new UnitPriceRule(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void negativePriceIsRejected() {
        assertThatThrownBy(() -> new UnitPriceRule(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }
}