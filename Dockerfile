FROM gradle:8.9.0-jdk21-alpine AS builder
WORKDIR /app

COPY . .
RUN gradle build -x test

FROM eclipse-temurin:21-jre-alpine

WORKDIR /apps

COPY --from=builder app/dependencies/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/application/ ./

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]