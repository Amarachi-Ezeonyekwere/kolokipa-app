# Future Improvements

Deliberately out of scope for the current build, documented here so
every decision to defer is visible and intentional, not forgotten or
hidden. Anyone cloning this repo is welcome to pick any of these up.

## Product features

### Circle duration & uneven contribution splits
Currently a circle has a fixed contributionAmount per member per cycle,
with no concept of a start/end date or a fixed number of total cycles.
Real groups often agree on a duration (e.g. "January to June") and
sometimes uneven per-member amounts. Would require a `totalCycles` or
`endDate` field on Circle, and optional per-member contribution
overrides.

### Real email delivery for invitations
Members are currently linked to accounts by email match on
registration (ADR-0025), but no actual email is sent inviting them —
sharing the registration link is a manual, out-of-band step today.
Real delivery via AWS SES belongs in the infrastructure phase, once a
verified sending domain exists.

### Role-based permissions within a circle
Any authenticated user linked to a circle can currently perform any
action on it — there's no distinction between the circle's owner
(should be able to start cycles, add/remove members) and an ordinary
member (should only mark their own contribution paid). Tracked since
ADR-0025.

### Password reset / forgot password flow
No mechanism exists today for a user who forgets their password to
recover their account.

### Email verification on registration
Accounts are usable immediately on registration with no email
ownership verification.

### Refresh tokens / persistent sessions
JWTs expire after 24 hours with no refresh mechanism — a user must log
in again from scratch once expired, rather than being silently
re-issued a new token.

### Contribution reminders
Notifications ahead of a cycle's deadline (the original product vision)
are not built — missed-payment detection is retrospective only, not
proactive.

### Search, filtering, and pagination
The dashboard and contribution lists load everything at once, with no
pagination. Fine at current scale; would need addressing before a
circle or user base grew significantly.

### Audit log
No record exists of who performed which action (created a circle,
marked a payment) beyond the data's own timestamps — no dedicated
audit trail.

## Backend architecture

### Report aggregation performance
`ReportService` computes summaries by pulling every contribution row
into memory and summing in Java rather than using SQL-level
aggregation (ADR-0020). Fine at current scale; would need revisiting
with real aggregate queries or a summary table at production volume.

### Admin sweep endpoint has no access control
`POST /admin/sweep-missed-contributions` (ADR-0022) is reachable by
anyone today — there is no concept of an admin role yet to restrict it
to. Must be locked down before any real deployment.

### JWT secret handling
The signing secret is currently supplied via a local `.env` file
(ADR-0024) — correct for local development, but must move to AWS
Secrets Manager before any real deployment, with the secret rotated at
that point rather than reused.

## Testing

### No integration tests against a real database
Current tests are unit tests only, using Mockito to isolate business
logic from real dependencies (ADR-0026). No tests currently exercise a
real HTTP request through a real database. A more complete setup would
use Testcontainers to spin up a disposable Postgres instance per test
run, rather than depending on a developer's local database being up.

### No frontend test coverage
No automated tests exist for the Next.js frontend, component
rendering, form validation, or client-side logic are only verified
manually.

## Frontend / UX

### Hero and community imagery sourced manually
Landing page photography was manually downloaded and placed rather
than pulled through any managed image pipeline or CMS, fine for a
static portfolio build, not a pattern that scales to a real content
team.

### Accessibility audit
Beyond one specific contrast fix (the quote section), no systematic
accessibility audit (screen reader testing, full keyboard navigation,
WCAG compliance check) has been performed.

### No offline / PWA support
The app requires a live connection at all times; no offline-first or
installable-app behavior exists.

