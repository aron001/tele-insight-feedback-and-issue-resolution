# Use a base image with Java and Maven pre-installed
FROM maven:3.8.4-openjdk-17-slim AS build
RUN apt-get update && apt-get install -y git

# Set the working directory inside the container
WORKDIR /app

# Copy the parent pom.xml file and the service-discovery module
COPY pom.xml .
COPY service-gateway/pom.xml ./service-gateway/

# Copy the source code
COPY service-gateway/src ./service-gateway/src

# Build the application
RUN mvn clean install -f service-gateway/pom.xml -DskipTests

# Set the base image for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/service-gateway/target/service-gateway*.jar ./service-gateway.jar
EXPOSE 8080
# Set the command to run the application
CMD ["java", "-jar", "service-gateway.jar"]
# nuu gjhg