# ADR-0002: Spring Boot Backend, Next.js Frontend

## Status
Accepted

## Context
KoloKipa is being built solo, from scratch, by a developer with prior
hands-on Spring Boot experience (via the PetClinic project) but no prior
experience owning a frontend build. The stack needs to be both learnable
solo and recognizable to companies hiring for backend/cloud/DevOps roles.

## Decision
Use Spring Boot (Java) for the backend REST API, and Next.js (TypeScript,
App Router) for the frontend, communicating over a documented REST API.

## Consequences
- Reuses existing Spring Boot familiarity rather than learning a new backend
  language at the same time as learning frontend development.
- Next.js and TypeScript are widely adopted, keeping the stack recognizable
  to employers rather than niche.
- Two languages (Java, TypeScript) means slightly more context-switching
  than a single-language full stack (e.g. Node + Node), accepted as a
  reasonable trade-off given the backend experience already in hand.