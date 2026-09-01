# ADR-0018: Cycle Creation No Longer Accepts Client-Supplied Cycle Number or Collector

## Status
Accepted

## Context
Cycle creation originally required the client to supply both cycleNumber
and collectorMemberId. This defeats the purpose of a "rotating" savings
circle — nothing prevented the same member from being picked repeatedly,
or a client requesting an arbitrary cycle number out of sequence.

## Decision
POST /circles/{circleId}/cycles now takes no request body. The server
assigns payout positions to members (by join order) the first time a
circle starts a cycle, then computes both the next cycle number and the
correct collector via a rotation formula:
((cycleNumber - 1) % memberCount) + 1.

## Consequences
- Removes an entire class of client-side misuse (skipping members,
  repeating a collector, picking arbitrary cycle numbers).
- CycleCreateRequest DTO was deleted as no longer needed.
- Adding or removing members after positions are assigned does not
  currently reshuffle existing positions — a known scope boundary,
  consistent with ADR-0016's treatment of mid-cycle membership changes.