# ADR-0026: Unit Tests Scoped to Core Business Logic, Not Full Coverage

## Status
Accepted

## Context
KoloKipa's highest-risk logic is the rotation algorithm (CycleService)
and auto-completion (ContributionService) — subtle bugs here would be
easy to miss by eye and directly undermine the product's core promise
of fairness and accurate tracking. Full integration testing (a real
test database, full HTTP request cycles) and unit tests for every
service were considered out of scope for the current phase.

## Decision
Unit tests were written for CycleService's rotation math and
ContributionService's auto-completion logic, using Mockito to isolate
each service from its real dependencies. Simpler CRUD-style services
(CircleService, MemberService) and integration-level testing are
deferred.

## Consequences
- The two areas of genuine business-logic risk are verified by
  automated tests, catching regressions before manual testing would.
- Broader test coverage remains a known, deliberate gap — tracked in
  docs/FUTURE_IMPROVEMENTS.md — appropriate for a solo portfolio project
  moving into its infrastructure phase, not a production system.