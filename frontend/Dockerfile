FROM node:24-alpine AS build
WORKDIR /app

# Dependencies first, in their own cacheable layer
COPY package*.json ./
RUN npm ci

COPY . .

# NEXT_PUBLIC_* values are compiled into the browser bundle during `next build`,
# so they must be build arguments, not runtime settings. The default keeps local
# docker compose working; the pipeline passes /api for AWS.
ARG NEXT_PUBLIC_API_URL=http://localhost:8080
ENV NEXT_PUBLIC_API_URL=$NEXT_PUBLIC_API_URL
ENV NEXT_TELEMETRY_DISABLED=1
RUN npm run build

FROM node:24-alpine
WORKDIR /app
ENV NODE_ENV=production \
    NEXT_TELEMETRY_DISABLED=1 \
    HOSTNAME=0.0.0.0 \
    PORT=3000
COPY --from=build --chown=node:node /app/public ./public
COPY --from=build --chown=node:node /app/.next/standalone ./
COPY --from=build --chown=node:node /app/.next/static ./.next/static
USER node
EXPOSE 3000
CMD ["node", "server.js"]
