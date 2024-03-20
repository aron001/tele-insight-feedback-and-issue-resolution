# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-17-slim AS build
RUN apt-get update && apt-get install -y git

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml file and the service-discovery module
COPY pom.xml .
COPY service-discovery/pom.xml ./service-discovery/

# Copy the source code
COPY service-discovery/src ./service-discovery/src

# Build the application
RUN mvn clean install -f service-discovery/pom.xml -DskipTests

# Set the base image for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/service-discovery/target/service-discovery*.jar ./service-discovery.jar
EXPOSE 8761
# Set the command to run the application
CMD ["java", "-jar", "service-discovery.jar"]
