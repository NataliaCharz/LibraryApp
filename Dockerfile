FROM gradle:8.9.0-jdk21-alpine AS builder
WORKDIR /app
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle settings.gradle
COPY src src

#RUN chmod +x gradlew
#RUN ./gradlew clean build -x test
RUN java -Djarmode=layertools --enable-preview -jar build/libs/library-0.0.1-SNAPSHOT.jar extract

FROM eclipse-temurin:21-jre-alpine
WORKDIR /apps
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/application/ ./
EXPOSE 8090
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]


#RUN java -Djarmode=layertools -jar build/libs/library-0.0.1-SNAPSHOT.jar extract
#
#FROM eclipse-temurin:21-jre-alpine
#WORKDIR /apps
#
#COPY --from=builder /app/dependencies/ ./
#COPY --from=builder /app/snapshot-dependencies/ ./
#COPY --from=builder /app/spring-boot-loader/ ./
#COPY --from=builder /app/application/ ./
#
#EXPOSE 8090
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]