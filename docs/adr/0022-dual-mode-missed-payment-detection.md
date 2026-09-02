# ADR-0022: Missed Payments Detected via Scoped Read-Time Sweep and Scheduled Background Sweep

## Status
Accepted

## Context
A contribution should transition to MISSED once its cycle's deadline has
passed. Relying solely on a periodic background job risks serving stale
data between runs; relying solely on read-time checks means circles
nobody views never get updated at all.

## Decision
ContributionService exposes a scoped sweep (one circle) invoked at the
start of every report-reading method, and a global sweep invoked hourly
by a Spring @Scheduled job. Both call the same underlying logic.

## Consequences
- Reports are always accurate the moment they're viewed, regardless of
  scheduler timing.
- The scheduled job keeps data consistent even for circles nobody is
  actively looking at.
- POST /admin/sweep-missed-contributions currently has no access
  control. This must be restricted to admin users once authentication
  exists (Milestone 11) — tracked here as a known, temporary gap.