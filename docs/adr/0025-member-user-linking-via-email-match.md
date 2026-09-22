# ADR-0025: Members Linked to User Accounts by Email Match

## Status
Accepted

## Context
Only a circle's creator could log in and view it. Real usage requires
every genuine member of a savings circle to see shared progress and mark
their own contributions, without the creator relaying updates manually.

## Decision
Member gains a nullable userId. When a Member is added with an email
matching an existing User, it links immediately. When a new User
registers, any unlinked Member records sharing that email link
automatically. GET /circles now returns circles the user owns or is
linked to as a member, via a single query.

## Consequences
- A person invited by email automatically sees every circle they were
  added to, the moment they register or log in with that same email —
  no separate "accept invite" step required.
- Actual email delivery (notifying the person they were added) is
  deferred to the AWS infrastructure milestone (SES), since production
  email sending is genuinely an infra concern. Until then, sharing the
  registration link is a manual, out-of-band step.
- Deeper permission distinctions (owner-only actions like starting a
  cycle vs. member-only actions like marking their own contribution
  paid) are not yet enforced server-side — any authenticated linked user
  can currently perform any circle action. Tracked as a known gap in
  docs/FUTURE_IMPROVEMENTS.md, not built now to keep scope disciplined.