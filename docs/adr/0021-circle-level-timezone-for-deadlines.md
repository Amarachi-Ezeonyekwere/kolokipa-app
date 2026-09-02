# ADR-0021: Circle-Level IANA Timezone for Deadline Calculations

## Status
Accepted

## Context
Cycle deadlines are calculated as "one cycle-frequency period after the
cycle starts." Calendar math (adding a month or week) is only meaningful
relative to a specific timezone, and a circle's members share one real-
world region and expectation of "when the deadline is."

## Decision
Circle stores a timezone as an IANA zone id (e.g. "Africa/Lagos"), set at
creation, defaulting per terminology profile in the frontend (mirroring
ADR-0010's currency-per-terminology pattern). Cycle.dueDate is computed
by resolving the cycle's start instant into that zone, adding the
appropriate calendar period, then converting back to a stored Instant
(UTC).

## Consequences
- Deadlines behave correctly across daylight saving changes and
  variable month lengths, since calendar arithmetic happens in a real
  timezone, not a raw UTC offset.
- All stored instants remain unambiguous UTC values; only display and
  calculation logic are timezone-aware — avoids the common bug of
  storing local time directly.