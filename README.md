# Checkout Kata

![CI](https://github.com/denilsontafa/checkout-kata/actions/workflows/ci.yml/badge.svg)

A supermarket checkout in Java. Items are identified by SKU (A, B, C, D). Some items
have multi-buy offers, for example three A's for 130p. Pricing rules are passed in
when a checkout starts, so prices and offers can change without touching the checkout code.

## Requirements

- Java 17
- Maven 3.8+

## Run the tests

```
mvn test
```

## Run the program

```
mvn -q compile
java -cp target/classes com.cdl.checkout.Main
```

Type items such as `BAB` and press Enter. A running total is printed after each scan.
Type `quit` to finish and print the final total.

Example session:

```
Scan items (A-D), 'quit' to finish.
BAB
B scanned. Running total: 30p
A scanned. Running total: 80p
B scanned. Running total: 95p
quit
Final total: 95p
```

The running total can rise by less than an item's price: the second B completes the
"2 for 45" offer.

## Sample prices

| Item | Unit price | Special price |
| ---- | ---------- | ------------- |
| A    | 50         | 3 for 130     |
| B    | 30         | 2 for 45      |
| C    | 20         |               |
| D    | 15         |               |

## Design

- `Checkout` counts scanned items and asks a `PricingRule` for the price of each item.
  It knows nothing about how a price is calculated.
- `PricingRule` is a one-method interface: `priceFor(count)`.
- `UnitPriceRule` prices every item at the unit price.
- `MultiBuyRule` applies a group offer, charging the offer price for each full group and
  the unit price for the remainder (4 A's = 130 + 50 = 180).
- `DefaultPricing` holds the sample price list in one place, used by the program and the tests.
- `Main` is the command-line layer only. It takes its input and output streams as
  parameters so it can be tested without a keyboard.

## Design decisions

- Money is whole pence (`long`), never `double`, to avoid floating-point rounding errors.
- Pricing is injected, not hardcoded, because prices change frequently.
- Counts are stored per SKU, so the order of scanning never affects the total.
- The total is recalculated from the counts, so the running total is always correct.
- Rules validate their inputs on creation (quantity of at least 2, positive prices),
  so a bad price list fails immediately with a clear message.
- A new kind of offer is a new `PricingRule` class. `Checkout` does not change.

## Assumptions

- SKUs are single letters; lowercase input is treated as uppercase.
- An unknown SKU is rejected with a clear message, and the program carries on.
- Prices must be positive.
- One `Checkout` is used per customer transaction; it is not shared between threads.

## What I would do next

- Load the price list from a file or a pricing service instead of `DefaultPricing`.
- Add other offer types (percentage discounts, offers across different items) as new rules.
- Use `BigDecimal` and an explicit rounding mode if percentage discounts are added.
- Add logging and a receipt showing which offers were applied.
