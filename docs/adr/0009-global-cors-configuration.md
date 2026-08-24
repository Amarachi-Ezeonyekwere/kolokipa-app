# ADR-0009: Centralized CORS Configuration Instead of Per-Controller Annotations

## Status
Accepted

## Context
The frontend (localhost:3000) and backend (localhost:8080) run on different
origins, which browsers block by default for security. An initial fix used
per-controller `@CrossOrigin` annotations, which would require repeating the
same configuration on every new controller going forward.

## Decision
Use a single `WebConfig` class implementing `WebMvcConfigurer`, applying one
CORS policy globally via `addCorsMappings`, rather than annotating each
controller individually.

## Consequences
- Adding new controllers automatically inherits the correct CORS policy with
  zero additional configuration.
- One place to update when the allowed origin changes (e.g., moving from
  localhost to a real production frontend URL during deployment).
- The allowed origin is currently hardcoded to localhost:3000 for local
  development; this must be updated to the real frontend URL before or
  during the ECS Fargate deployment milestone.