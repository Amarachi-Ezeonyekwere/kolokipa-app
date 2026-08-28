# ADR-0014: Cycle Collector Must Belong to the Same Circle

## Status
Accepted

## Context
A Cycle references a collecting Member independently of the Circle it
belongs to at the database level (two separate foreign keys). Without an
explicit check, it would be possible to assign a member from a different
circle as a cycle's collector, producing data that is technically valid
but semantically meaningless.

## Decision
CycleService validates, at creation time, that the collector's own
Circle matches the Circle the cycle is being created for. A mismatch
throws IllegalArgumentException, surfaced to the API as a 400 Bad
Request via GlobalExceptionHandler.

## Consequences
- Prevents a class of data integrity bugs that the database schema alone
  cannot enforce (foreign keys don't know about cross-entity business
  rules).
- The check happens in the service layer, not the database, meaning it's
  only enforced through this code path — any future direct database
  writes or bulk operations would need to reapply this rule themselves.