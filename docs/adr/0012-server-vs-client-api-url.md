# ADR-0012: Separate Internal and Public API URLs for Server vs. Client Fetches

## Status
Accepted

## Context
The dashboard's data fetch runs inside the Next.js server (a Server
Component), executing inside the frontend Docker container itself — not
in the browser. `http://localhost:8080` resolves differently depending on
where the code runs: from the browser it correctly reaches the backend
container via Docker's port mapping, but from inside the frontend
container it refers to the frontend container itself, where nothing is
listening on port 8080. This caused circle creation (a browser-side
fetch) to work while the dashboard's initial load (a server-side fetch)
silently failed and fell back to an empty state.

## Decision
Use two environment variables: `NEXT_PUBLIC_API_URL` (exposed to the
browser, used for client-side fetches) and `INTERNAL_API_URL` (server-only,
resolves via Docker's internal network as `http://backend:8080`). The API
helper (`lib/api.ts`) detects execution context via `typeof window ===
"undefined"` and selects the correct URL accordingly.

## Consequences
- Server Components and client components both reach the backend
  correctly, regardless of where their code actually executes.
- Adds a small amount of conditional logic to the API helper, which must
  be understood by anyone extending it later.
- This split is Docker-networking-specific; the distinction may need
  revisiting once deployed to ECS Fargate, where both the frontend and
  backend will have real, non-localhost addresses.