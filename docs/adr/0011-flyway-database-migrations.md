# ADR-0011: Flyway for Schema Migrations Instead of Hibernate ddl-auto

## Status
Accepted

## Context
`ddl-auto: update` inferred schema changes automatically but failed
silently when adding a `nullable = false` column to a table that already
contained data — Hibernate logged a warning and left the schema out of
sync with the entities, causing a runtime failure only discovered later.

## Decision
Use Flyway for all schema changes, with versioned SQL migration files
under `db/migration`. Hibernate's `ddl-auto` is set to `validate`, so
startup fails loudly if entities and schema disagree, instead of silently
drifting. Spring Boot 4.x requires the dedicated `spring-boot-starter-flyway`
starter (not `flyway-core` alone) plus `flyway-database-postgresql`.

## Consequences
- Every schema change is an explicit, reviewable, version-controlled SQL
  file (`V1__`, `V2__`, ...), never inferred or guessed.
- Mismatches between entities and the real schema now fail fast at
  startup rather than surfacing as a confusing runtime database error.
- Required a one-time local reset (`docker compose down -v`) to let Flyway
  build the schema from a clean slate; this is a local-dev-only action,
  never appropriate against real data.