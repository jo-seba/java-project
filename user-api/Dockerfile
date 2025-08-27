FROM gradle:8-jdk17 AS builder
WORKDIR /app


# Root Gradlew
COPY build.gradle ./
COPY settings.gradle ./

# multi module api gradle
COPY user-api/build.gradle ./user-api/

RUN gradle build -x test || return 0

# multi module source
COPY user-api/src ./user-api/src

RUN gradle :user-api:build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/user-api/build/libs/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
