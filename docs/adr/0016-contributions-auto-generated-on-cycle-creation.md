# ADR-0016: Contributions Are Auto-Generated When a Cycle Is Created

## Status
Accepted

## Context
A Cycle represents one round of a savings circle. For the tracker to be
useful, every member must have a trackable obligation for that round the
moment it starts — leaving this as a manual step risks cycles existing
with incomplete or missing contribution records.

## Decision
CycleService.createCycle generates one Contribution per circle member
immediately after saving the cycle, each in PENDING status, with amount
set from the circle's current contributionAmount at that moment.

## Consequences
- No cycle can exist without a complete, correct set of contributions —
  removes a class of "forgot to add someone" bugs.
- Adding or removing members after a cycle has started does not
  retroactively adjust that cycle's contributions — a deliberate scope
  boundary for now, revisited if mid-cycle membership changes become a
  real requirement.