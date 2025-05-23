FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean test

FROM selenium/standalone-chrome:latest
WORKDIR /app
COPY --from=build /app /app