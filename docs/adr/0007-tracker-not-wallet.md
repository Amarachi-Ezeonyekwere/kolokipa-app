# ADR-0007: KoloKipa Is a Tracker, Not a Wallet or Payment Processor

## Status
Accepted

## Context
Rotating savings groups involve real money changing hands between members.
Building an application that actually moves or holds money introduces
significant financial regulatory, licensing, and security obligations
that are unrealistic for a solo portfolio project.

## Decision
KoloKipa only tracks and records contributions, payout rotations, and
member participation. It never processes, holds, or transfers real money.
Payment status (e.g. "paid") is manually marked by users, not verified
via a payment gateway.

## Consequences
- Avoids fintech/payment-processor regulatory scope entirely, keeping the
  project realistic for a solo developer to build and maintain.
- Trust in payment status relies on member/organizer honesty rather than
  system-verified transactions — an accepted and explicitly communicated
  limitation, not a gap to be "fixed" later without a scope change.