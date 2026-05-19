FROM gradle:8.9.0-jdk21-alpine AS builder
WORKDIR /app

COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/entrypoint.sh"]