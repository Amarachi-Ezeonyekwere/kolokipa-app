# ADR-0004: UUID Primary Keys Instead of Auto-Incrementing IDs

## Status
Accepted

## Context
Entity primary keys can be auto-incrementing integers or generated UUIDs.
KoloKipa's entities (Circle, Member, etc.) will be referenced in API URLs
and potentially shared/exposed externally.

## Decision
Use `UUID` (via `@GeneratedValue`) as the primary key type for all entities,
rather than auto-incrementing `Long` IDs.

## Consequences
- IDs exposed in URLs or API responses do not reveal how many records exist
  (an incrementing ID like `47` leaks record counts; a UUID does not).
- Safer default for any future public-facing or shareable API surface.
- Slightly larger storage footprint per row and less human-readable IDs
  during manual debugging, accepted as a reasonable trade-off.