# ADR-0017: Automatic MISSED/LATE Detection Deferred to Rotation Logic Milestone

## Status
Accepted

## Context
ContributionStatus includes MISSED and LATE values, but determining them
requires comparing a contribution's paid date against a cycle's deadline
— logic that depends on the rotation/cycle-advancement behavior not yet
built.

## Decision
This milestone only implements PENDING -> PAID, triggered by an explicit
mark-as-paid action. MISSED and LATE transitions are deferred to the
rotation logic milestone, where cycle deadlines and advancement are
defined.

## Consequences
- Contribution status is manually driven for now; automatic detection of
  missed payments is a known, tracked gap, not an oversight.
- Keeps this milestone focused on the tracking mechanism itself before
  layering time-based business rules on top.