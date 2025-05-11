FROM gradle:8-jdk17 AS builder
WORKDIR /app


# Root Gradlew
COPY build.gradle ./
COPY settings.gradle ./

# multi module api gradle
COPY api/build.gradle ./api/

RUN gradle build -x test || return 0

# multi module source
COPY api/src ./api/src

RUN gradle :api:build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/api/build/libs/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
