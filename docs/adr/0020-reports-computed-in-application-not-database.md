# ADR-0020: Report Aggregations Computed in the Application Layer

## Status
Accepted

## Context
Circle summary (totals, completion rate) requires aggregating across all
contributions for a circle. This can be done via SQL aggregate queries
(SUM, COUNT) or by fetching rows and computing in Java.

## Decision
ReportService fetches contributions and cycles via existing repository
methods and computes totals/rates in Java, rather than writing dedicated
aggregate SQL queries or database views.

## Consequences
- Simpler to read, test, and reason about at current data volume.
- Not the correct approach at production scale — pulling every
  contribution row into memory to sum them does not scale to circles
  with large contribution histories. Revisit with SQL-level aggregation
  (or a materialized summary table) if/when performance requires it.
- Missed-payment reporting is intentionally absent from this milestone;
  it depends on cycle deadline logic not yet built (see ADR-0017).