# ADR-0015: Cycle Status and Start Date Are Server-Controlled, Not Client-Supplied

## Status
Accepted

## Context
A newly created cycle always begins in the UPCOMING state, and its start
date is the moment of creation. Allowing a client to set these directly
would let a request claim a cycle is already COMPLETED or backdate its
start, bypassing the actual rotation logic that will govern status
transitions in a later milestone.

## Decision
CycleCreateRequest excludes status and startDate entirely.
CycleService always sets status to UPCOMING and startDate to the current
server time when a cycle is created.

## Consequences
- Status transitions (UPCOMING → ACTIVE → COMPLETED) must go through
  dedicated logic in a future milestone, rather than being client-settable
  at any point — keeping the rotation state machine authoritative on the
  server.
- Simpler request payloads for the client, since only genuinely
  client-owned data (cycle number, collector) needs to be supplied.