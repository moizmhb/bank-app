# Stage 1: Build stage
FROM maven:3.8.4-openjdk-11 as builder
WORKDIR /build
COPY . .
RUN mvn clean package

# Stage 2: Production stage
FROM openjdk:11-jre-slim
WORKDIR /bank
COPY --from=builder /build/target/*.jar bankapplication.jar
ENTRYPOINT ["java","-jar","/bank/bankapplication.jar"]