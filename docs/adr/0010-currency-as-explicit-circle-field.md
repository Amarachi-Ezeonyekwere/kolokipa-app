# ADR-0010: Currency Stored as an Explicit Field, Not Derived from Terminology

## Status
Accepted

## Context
KoloKipa supports regional terminology (Esusu, Chama, Stokvel, etc.) per
ADR-0006. Each region's terminology is strongly associated with a local
currency, but the two concepts are not the same thing — terminology
describes what a group is called, currency describes what it counts in.

## Decision
Add an explicit `currency` field to the `Circle` entity, set alongside
`terminologyProfile` at creation time, rather than inferring currency from
terminology at read time. The frontend auto-selects a sensible default
currency when a terminology is chosen, but the two remain independently
stored fields.

## Consequences
- Supports edge cases naturally, e.g. a diaspora group using "Esusu" as its
  name while operating in a non-Naira currency, without requiring a schema
  change later.
- Slight duplication of information at circle-creation time (currency is
  implied by terminology in the common case), accepted as a reasonable
  trade-off for future flexibility.
- Frontend currency-symbol display now depends on a lookup table
  (`CURRENCY_SYMBOLS`) staying in sync with whatever currencies the backend
  and terminology options support — a manual list to keep updated as more
  regions are added.