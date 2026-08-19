# ADR-0003: Radix UI as the shadcn/ui Primitive Library

## Status
Accepted

## Context
shadcn/ui's CLI supports multiple underlying primitive libraries: Radix UI,
Base UI, and React Aria. Base UI is now the CLI's recommended default. As a
solo developer still learning frontend development, troubleshooting support
availability matters more than being on the newest option.

## Decision
Use Radix UI as the component primitive library, rather than the
newer-but-CLI-recommended Base UI.

## Consequences
- Access to the largest existing base of tutorials, examples, and
  community troubleshooting content, since Radix has been shadcn's
  default for years.
- Slightly behind the CLI's own forward-looking default (Base UI), which
  may require a deliberate migration later if Base UI becomes the
  de facto standard industry-wide.