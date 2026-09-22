# KoloKipa

**Traditional savings. Modern accountability.**

A full-stack platform for African rotating savings circles — Esusu,
Chama, Stokvel, Tontine, Adashe, Equb — built solo, end to end:
architecture, domain modeling, API design, frontend, authentication,
and testing, now moving into production infrastructure.

See [`docs/BUSINESS_VALUE.md`](docs/BUSINESS_VALUE.md) for the problem
this solves and why it was built this way.

---

## What it does

- Create a savings circle with a contribution amount, cycle frequency,
  regional terminology, currency, and timezone
- Add members; anyone who registers with a matching email automatically
  gains access to every circle they were added to
- Start a cycle — the server assigns the collector automatically, by
  join order, guaranteeing no one is skipped or repeated
- Every member of a cycle gets an automatically generated contribution
  to pay; mark it paid, and the cycle auto-completes once everyone has
- Missed deadlines are detected automatically, timezone-aware, both on
  read and on an hourly background sweep
- A circle summary (collection rate, cycles completed, member count)
  and full per-member contribution history
- A public marketing landing page; an authenticated dashboard behind
  real login/registration

## Tech stack

**Backend:** Java 21, Spring Boot 4.1, Spring Security, Spring Data JPA,
PostgreSQL, Flyway (schema migrations), JWT authentication, JUnit 5 +
Mockito

**Frontend:** Next.js 16 (App Router), TypeScript, Tailwind CSS,
shadcn/ui (Radix primitives)

**Infrastructure :** Docker, Docker Compose, with
Terraform, AWS ECS Fargate, CI/CD, and DevSecOps tooling 
— see [Roadmap](#roadmap) below.

## Architecture

A layered backend (Controller → Service → Repository → Entity) with
every layer depending only on the one directly beneath it, and a
Next.js frontend split between server components (data fetching) and
client components (interactivity). Every significant architectural
decision — and the reasoning behind it — is documented as it was made
in [`docs/adr/`](docs/adr/), numbered sequentially from project start.

---

## Getting started

### Prerequisites

- Java 21 (SDKMAN recommended)
- Node.js 20+ and npm
- Docker and Docker Compose
- Git

### Clone the repository

```bash
git clone https://github.com/Amarachi-Ezeonyekwere/kolokipa-app.git
cd kolokipa-app
```

### Set up environment variables

Create a `.env` file at the project root:
JWT_SECRET=<generate one with: openssl rand -base64 48>


This file is intentionally excluded from version control; generate
your own secret rather than reusing any example value.

### Run the full stack with Docker

```bash
docker compose up --build
```

This starts PostgreSQL, the Spring Boot backend, and the Next.js
frontend together. Flyway applies all database migrations
automatically on backend startup.

- Frontend: [http://localhost:3000](http://localhost:3000)
- Backend API: [http://localhost:8080](http://localhost:8080)
- Interactive API docs (Swagger): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Running the frontend independently (faster iteration)

```bash
docker compose up -d backend postgres
cd frontend
npm install
npm run dev
```

## Running the tests

```bash
cd backend
./mvnw test
```

Unit tests cover the two areas of genuine business-logic risk — payout
rotation fairness and contribution auto-completion — using Mockito to
isolate each service from its real dependencies. See
[`docs/adr/0026-unit-testing-scope.md`](docs/adr/0026-unit-testing-scope.md)
for what's covered and what's deliberately deferred.

---

## Project structure
kolokipa-app/
├── backend/ Spring Boot API
│ └── src/main/java/com/kolokipa/backend/
│ ├── config/ Security and web configuration
│ ├── controller/ REST endpoints
│ ├── dto/ Request/response shapes
│ ├── entity/ Database-mapped domain objects
│ ├── repository/ Data access interfaces
│ ├── scheduler/ Background jobs
│ ├── security/ JWT generation and request auth
│ └── service/ Business logic
├── frontend/ Next.js application
│ └── src/
│ ├── app/ Pages (App Router)
│ ├── components/ Reusable UI components
│ └── lib/ API client and auth helpers
├── docs/
│ ├── adr/ Architecture Decision Records, numbered chronologically
│ ├── BUSINESS_VALUE.md What problem this solves and why
│ └── FUTURE_IMPROVEMENTS.md Known, deliberate scope gaps
└── docker-compose.yml


## Documentation

- [`docs/BUSINESS_VALUE.md`](docs/BUSINESS_VALUE.md) — the problem and
  why the product was shaped this way
- [`docs/adr/`](docs/adr/) — every significant architecture decision,
  in the order it was made, with context and consequences
- [`docs/FUTURE_IMPROVEMENTS.md`](docs/FUTURE_IMPROVEMENTS.md) — known
  gaps, deliberately deferred, and why

## Roadmap

This is version one — the application layer, complete and tested.
Currently deployed into:

- Terraform-provisioned AWS infrastructure (VPC, RDS, ECR, ECS Fargate,
  ALB)
- CI pipeline (build, test, lint) via GitHub Actions
- DevSecOps gates (SAST, dependency scanning, secrets scanning,
  container image scanning)
- CD to ECS Fargate
- Observability (CloudWatch — logs, metrics, alarms, dashboards)


## License

This project is licensed under the MIT License.

See the [LICENSE](LICENSE) file for details.


## Author

**Amarachi Ezeonyekwere** — Cloud & DevOps / SRE Engineer
[GitHub](https://github.com/Amarachi-Ezeonyekwere)

