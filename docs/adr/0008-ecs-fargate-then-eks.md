# ADR-0008: ECS Fargate as Initial Deployment Target, EKS/K8s as Future Migration

## Status
Accepted

## Context
The developer has existing hands-on Kubernetes/EKS experience from a prior
project. KoloKipa is intended to demonstrate a different, complementary
deployment path (container orchestration via a managed AWS-native service)
rather than repeating the same EKS setup.

## Decision
Deploy KoloKipa initially on ECS Fargate. Application code and Docker
images are built to remain portable to EKS or bare-metal Kubernetes as a
later, deliberate migration — not a rewrite.

## Consequences
- Demonstrates breadth across AWS container orchestration options (ECS
  Fargate vs. EKS) rather than only one, strengthening the platform/cloud
  engineering narrative.
- Application-level design (stateless containers, externalized config via
  Spring profiles, health check endpoints) is kept orchestrator-agnostic
  from the start, so a future EKS migration is a genuine infra swap, not
  an application rewrite.