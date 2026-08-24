# ADR-0013: BuildKit Cache Mounts for Maven and npm Dependencies

## Status
Accepted

## Context
Adding Flyway to `pom.xml` invalidated Docker's normal layer cache for
dependency resolution, forcing a full re-download of the entire Maven
dependency tree. Combined with unstable network conditions, this caused
build times exceeding 50 minutes and repeated failures from connection
timeouts to Maven Central. The separate `mvn dependency:go-offline` step
also pulled in a large, mostly irrelevant plugin dependency tree of its
own, unrelated to the application's actual dependencies.

## Decision
Use BuildKit cache mounts (`--mount=type=cache,target=/root/.m2` for
Maven, `--mount=type=cache,target=/root/.npm` for npm) on the dependency
resolution steps in both Dockerfiles. Removed the standalone
`mvn dependency:go-offline` step in favor of a single `mvn clean package`
call with the cache mount attached.

## Consequences
- Downloaded dependencies persist across builds independently of Docker's
  layer cache, so changes to `pom.xml` or `package.json` no longer force
  a full re-download of previously-fetched packages.
- Fewer artifacts are downloaded overall, since the plugin-only
  dependency tree pulled in by `go-offline` is no longer resolved
  separately.
- Requires BuildKit to be active (default in current Docker Compose);
  cache mounts are local to the machine building the image, so CI
  runners will need their own equivalent caching strategy configured
  later, rather than inheriting this local cache.