# ADR-0019: Cycles Auto-Complete When All Contributions Are Paid

## Status
Accepted

## Context
A cycle's lifecycle (UPCOMING -> COMPLETED) was previously never
transitioned automatically — nothing marked a cycle finished even after
every member had paid, and nothing prevented starting an overlapping
cycle as a result.

## Decision
ContributionService.markAsPaid checks, after saving a payment, whether
every contribution in that cycle is now PAID. If so, it sets the cycle's
status to COMPLETED and stamps endDate immediately, as part of the same
request.

## Consequences
- A cycle's status is always an accurate reflection of its payment
  state, with no manual step required.
- This check runs on every single mark-as-paid call, doing a full
  re-query of that cycle's contributions each time — acceptable at
  current scale, worth revisiting if contribution volume per cycle grows
  significantly.