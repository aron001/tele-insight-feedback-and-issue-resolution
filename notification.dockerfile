# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-17-slim AS build
RUN apt-get update && apt-get install -y git

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml file and the service-discovery module
COPY pom.xml .
COPY notification-service/pom.xml ./notification-service/

# Copy the source code
COPY notification-service/src ./notification-service/src

# Build the application
RUN mvn clean install -f notification-service/pom.xml -DskipTests

# Set the base image for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/notification-service/target/notification-service*.jar ./notification-service.jar
EXPOSE 8084
# Set the command to run the application
CMD ["java", "-jar", "notification-service.jar"]