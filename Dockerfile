# syntax=docker/dockerfile:1

################################################################################
# Stage 1: Build Tailwind CSS
# Uses Node.js to install dependencies and build the static CSS assets
################################################################################
FROM node:20-alpine AS tailwind-build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY src ./src
# Build CSS - output is likely src/main/resources/static/css/output.css
RUN npm run build:css

################################################################################
# Stage 2: Resolve Dependencies
# Uses a cache mount to speed up Maven dependency downloads
################################################################################
FROM eclipse-temurin:21-jdk-jammy as deps
WORKDIR /build
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -DskipTests

################################################################################
# Stage 3: Build Application
# Compiles the Java code and packages the application
################################################################################
FROM deps as package
WORKDIR /build
COPY ./src src/
# Copy the built CSS from the tailwind-build stage
COPY --from=tailwind-build /app/src/main/resources/static/css/output.css ./src/main/resources/static/css/output.css

RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################
# Stage 4: Extract Layers
# Extracts Spring Boot layers for efficient container caching
################################################################################
FROM package as extract
WORKDIR /build
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################
# Stage 5: Runtime
# Minimal runtime image with non-root user
################################################################################
FROM eclipse-temurin:21-jre-jammy AS final

# Create a non-privileged user
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

WORKDIR /app

# Copy the extracted layers
COPY --from=extract build/target/extracted/dependencies/ ./
COPY --from=extract build/target/extracted/spring-boot-loader/ ./
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract build/target/extracted/application/ ./

COPY entrypoint.sh ./

EXPOSE 8080

ENTRYPOINT [ "./entrypoint.sh", "java", "org.springframework.boot.loader.launch.JarLauncher" ]
