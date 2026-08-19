# ADR-0005: BigDecimal for All Monetary Fields

## Status
Accepted

## Context
KoloKipa tracks contribution amounts, which represent real money values
even though the platform itself never moves money (see ADR-0007).
Floating-point types (`double`, `float`) are known to introduce rounding
errors in financial calculations.

## Decision
Use `java.math.BigDecimal` for all fields representing monetary amounts
(e.g. `Circle.contributionAmount`), never `double` or `float`.

## Consequences
- Eliminates floating-point rounding error risk in any amount comparisons
  or calculations.
- Correct practice even though KoloKipa is a tracker, not a payment
  processor — money-shaped data deserves money-safe types regardless of
  whether real transactions occur.