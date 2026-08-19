# ADR-0006: Regional Terminology Modeled as Data, Not Hardcoded Strings

## Status
Accepted

## Context
Rotating savings groups have different regional names across Africa
(Esusu/Ajo in Nigeria, Susu in Ghana, Chama in Kenya, Stokvel in South
Africa, etc.). KoloKipa wants to support this as a real feature, not a
cosmetic detail specific to one region.

## Decision
Store a `terminologyProfile` field on the `Circle` entity (e.g. "esusu",
"susu", "chama") rather than hardcoding a single term into the schema,
UI strings, or business logic. The underlying domain model (Circle,
Member, Contribution, etc.) stays identical regardless of terminology.

## Consequences
- A single domain model serves every regional naming convention; adding
  a new region later means adding a lookup entry, not a schema change.
- UI labels must be resolved dynamically based on this field rather than
  hardcoded, adding a small amount of frontend complexity in exchange for
  genuine cultural flexibility.