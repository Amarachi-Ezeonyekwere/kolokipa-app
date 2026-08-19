# ADR-0001: Monorepo for App, Separate Repo for Infra

## Status
Accepted

## Context
KoloKipa needs a backend (Spring Boot), frontend (Next.js), and infrastructure
(Terraform). As a solo developer, local development speed and clear separation
of change cadence both matter.

## Decision
Backend and frontend live in one repository (`kolokipa-app`) as subfolders,
orchestrated locally via a single docker-compose file. Infrastructure code
lives in a separate repository (`kolokipa-infra`).

## Consequences
- App code changes frequently and can be committed/iterated on fast without
  infra concerns mixed in.
- Infra changes are rarer and more deliberate — a Terraform plan/apply is
  reviewed on its own, not accidentally bundled into an app feature commit.
- One `docker compose up` brings up the full local app stack, since backend
  and frontend share a repo root.