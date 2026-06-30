# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY dependency-reduced-pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/system-monitor.jar app.jar

# Install required system libraries for OSHI
RUN apk add --no-cache procps lm-sensors

ENTRYPOINT ["java", "-jar", "app.jar"]
