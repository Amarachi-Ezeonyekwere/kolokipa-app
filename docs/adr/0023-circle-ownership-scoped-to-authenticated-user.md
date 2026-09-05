# ADR-0023: Circle Creation and Listing Scoped to the Authenticated Owner

## Status
Accepted

## Context
Prior to authentication, every circle was visible to every caller. With
real user accounts now in place, a circle should belong to whoever
created it, and the circle list should reflect only what that specific
user owns.

## Decision
Circle gains a required ownerId, a foreign key to users. CircleService
reads the current user's id from CurrentUserProvider (backed by the
JWT-authenticated request context) at creation time and at list time,
never accepting ownerId from client input.

## Consequences
- A user only ever sees their own circles in GET /circles; creation
  always attributes the correct owner automatically.
- Deeper endpoints (a specific circle's members, cycles, contributions,
  reports) remain reachable by any authenticated user for now, not yet
  restricted to the circle's owner or its members. Properly restricting
  those requires linking Member records to real User accounts (an
  invitation/membership feature not yet built) — a known, deliberate
  scope boundary, not an oversight.