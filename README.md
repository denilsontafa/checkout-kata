# Checkout Kata

A supermarket checkout in Java. Items are SKUs (A, B, C, D). Prices are passed in
when a checkout starts, so pricing can change without touching the checkout code.

## Run the tests
```
mvn test
```
(If your work Maven mirror blocks you: `mvn -s settings-public.xml test`)

## Run the program
```
mvn -q compile exec:java -Dexec.mainClass=com.cdl.checkout.Main
```
or compile by hand:
```
javac -d out $(find src/main -name "*.java") && java -cp out com.cdl.checkout.Main
```
Type items such as `ABBA`. You get a running total after each scan. Type `quit` to finish.

## Design decisions
- Money is `long` pence, never `double`.
- Prices are injected into `Checkout`; nothing is hardcoded in it.
- Counts per SKU are stored, so scan order never matters.
- The command-line layer (`Main`) is separate from the domain (`Checkout`).

## Assumptions
- SKUs are single letters; lowercase input is treated as uppercase.
- An unknown SKU is rejected with a clear message.

## What I would do next
(fill this in at the end)
