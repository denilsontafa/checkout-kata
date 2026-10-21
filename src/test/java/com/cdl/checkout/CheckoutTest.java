package com.cdl.checkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CheckoutTest {

    private Checkout newCheckout() {
        return new Checkout(DefaultPricing.rules());
    }

    private Checkout scanAll(String items) {
        Checkout checkout = newCheckout();
        for (char sku : items.toCharArray()) {
            checkout.scan(sku);
        }
        return checkout;
    }

    @Test
    void emptyBasketCostsNothing() {
        assertThat(newCheckout().total()).isZero();
    }

    @Test
    void singleItemsCostTheirUnitPrice() {
        assertThat(scanAll("A").total()).isEqualTo(50);
    }

    @Test
    void unknownItemIsRejected() {
        assertThatThrownBy(() -> newCheckout().scan('Z'))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void threeAsCost130() {
        assertThat(scanAll("AAA").total()).isEqualTo(130);
    }

    @Test
    void fourAsCost180() {
        assertThat(scanAll("AAAA").total()).isEqualTo(180);
    }

    @Test
    void sixAsCost260() {
        assertThat(scanAll("AAAAAA").total()).isEqualTo(260);
    }

    @Test
    void twoBsCost45() {
        assertThat(scanAll("BB").total()).isEqualTo(45);
    }

    @Test
    void threeBsCost75() {
        assertThat(scanAll("BBB").total()).isEqualTo(75);
    }

    @Test
    void bAbCosts95() {
        assertThat(scanAll("BAB").total()).isEqualTo(95);
    }

    @Test
    void orderIndependence() {
        assertThat(scanAll("BAB").total()).isEqualTo(95);

        assertThat(scanAll("ABB").total()).isEqualTo(95);
    }

    @Test
    void runningTotalIsCorrectAfterEachScan() {
        Checkout checkout = newCheckout();
        checkout.scan('B');
        assertThat(checkout.total()).isEqualTo(30);
        checkout.scan('A');
        assertThat(checkout.total()).isEqualTo(80);
        checkout.scan('B');
        assertThat(checkout.total()).isEqualTo(95);
    }
}
