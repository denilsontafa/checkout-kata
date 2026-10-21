package com.cdl.checkout;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Command-line layer only. Type items (e.g. ABBA); "quit" to exit. */
public class Main {

    public static void main(String[] args) {
        run(System.in, System.out, new Checkout(DefaultPricing.rules()));
    }

    static void run(InputStream in, PrintStream out, Checkout checkout) {
        out.println("Scan items (A-D), 'quit' to finish.");
        try (Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim().toUpperCase();
                if (line.equals("QUIT")) {
                    break;
                }
                for (char sku : line.toCharArray()) {
                    try {
                        checkout.scan(sku);
                        out.println(sku + " scanned. Running total: " + checkout.total() + "p");
                    } catch (IllegalArgumentException e) {
                        out.println("Error: " + e.getMessage());
                    }
                }
            }
        }
        out.println("Final total: " + checkout.total() + "p");
    }
}
