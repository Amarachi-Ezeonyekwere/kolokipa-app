# Business Value & Problem Statement

## The problem

Across Africa, rotating savings groups; Esusu, Ajo, Adashe (Nigeria),
Susu (Ghana), Chama (Kenya), Stokvel (South Africa), Tontine
(Francophone West Africa), are one of the oldest, most trusted
community finance mechanisms in existence. A group of people contribute
a fixed amount on a fixed schedule; one person collects the full pot
each round, in rotation, until everyone has had a turn.

These groups are almost always run on memory, WhatsApp threads, and
notebooks. That works, until it doesn't:

- No shared, trusted record of who has paid
- Disputes over whose turn it is to collect
- No visibility into a group's overall health
- No accountability when someone falls behind
- The entire system depends on one person (usually the organizer)
  remembering everything correctly, forever

None of this is a technology failure; it's a tooling gap. The trust
and tradition are real. The record-keeping isn't built for it.

## What KoloKipa solves

KoloKipa is not a bank, a wallet, or a payment processor; deliberately.
It never touches real money. It solves the actual problem: **shared,
accurate, tamper-resistant record-keeping** for a system that already
works, by:

- **Removing memory as a single point of failure.** Every contribution,
  every collector, every missed payment is recorded once, visible to
  everyone in the circle — not held in one organizer's head or one
  phone's chat history.
- **Enforcing fairness automatically.** The rotation order is computed,
  not chosen — no one can be skipped, and no one can collect twice
  before everyone else has had a turn. This is a rule the tradition
  already has; KoloKipa just makes it impossible to violate by accident
  or bias.
- **Making accountability visible, not accusatory.** A missed payment
  is a fact on a shared screen, not something one person has to
  confront another about from memory.
- **Respecting that this is not one tradition.** The same underlying
  mechanism is called different things in different places. KoloKipa's
  data model treats terminology, currency, and timezone as first-class,
  regional choices — not a single hardcoded assumption.

## Why this matters as an engineering decision, not just a feature list

Every one of the above is a product decision that shaped a technical
one, and the ADRs in `docs/adr/` trace that line directly:

- "Never touch real money" → no payment gateway integration, no PCI
  scope, a realistic build for a solo engineer (ADR-0007)
- "Fairness must be enforced, not requested" → rotation computed
  server-side from join order, never client-supplied (ADR-0018)
- "Don't assume one region's language" → terminology and currency
  modeled as data per circle, not hardcoded (ADR-0006, ADR-0010)
- "A missed payment must be a fact, not a guess" → timezone-aware
  deadlines and automatic detection, not manual flagging (ADR-0021,
  ADR-0022)

## The broader point

Cloud, DevOps, SRE, and platform engineering don't exist in a vacuum —
they exist to reliably deliver something that solves a real problem for
real people. Understanding *why* a system needs to be reliable, secure,
and well-architected requires first understanding *what it's actually
for*. KoloKipa was built end-to-end problem, product, architecture,
implementation  specifically to demonstrate that connection, before
the infrastructure work that makes it production-ready begins.