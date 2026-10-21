package com.cdl.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void printsRunningTotalAfterEachScan() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Checkout checkout = new Checkout(DefaultPricing.rules());
        Main.run(new ByteArrayInputStream("AB\nquit\n".getBytes()), new PrintStream(buffer), checkout);

        assertThat(buffer.toString()).contains("A scanned. Running total: 50p")
                .contains("B scanned. Running total: 80p")
                .contains("Final total: 80p");
    }
}
