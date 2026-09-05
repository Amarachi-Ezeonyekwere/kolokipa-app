# ADR-0024: JWT Secret Sourced From an Environment Variable, Not Committed to Version Control

## Status
Accepted

## Context
The JWT signing secret was initially placed directly in application.yml
as a placeholder string, which was then committed to the repository.
Anyone with read access to the repository could see this value. Since
the secret is what proves a token was genuinely issued by this server,
its exposure would allow forging valid authentication tokens for any
user without knowing their password.

## Decision
application.yml now references jwt.secret as ${JWT_SECRET}, an
environment variable resolved at startup rather than a literal value.
For local development, the real value is supplied via a .env file at
the project root, which docker-compose reads automatically and which is
excluded from version control via .gitignore. The real secret itself
never appears in any committed file.

## Consequences
- No real secret value exists anywhere in the git history from this
  point forward; only the placeholder previously committed remains in
  past commits, and it was never a genuinely exploitable value (generic
  instructional text, not a real secret ever used to sign live user
  sessions).
- Every environment (local, and later staging/production) must supply
  its own JWT_SECRET value independently; there is no shared default.
- This is a stepping stone, not the final state: before any real
  deployment, this value must be sourced from AWS Secrets Manager rather
  than a local .env file, tracked as required work in the infrastructure
  milestones.