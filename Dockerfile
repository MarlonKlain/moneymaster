# --- Stage 1: The Build Stage ---
# Use a Maven image that includes the Java JDK to build our project
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file first to leverage Docker's layer caching
COPY pom.xml .

# Copy the rest of your source code
COPY src ./src

# Run the Maven command to build the project and create the .jar file
# The -DskipTests flag is used to speed up the build process in deployment
RUN mvn package -DskipTests


# --- Stage 2: The Final, Lean Image ---
# Use a much smaller image that only contains the Java Runtime Environment (JRE)
FROM eclipse-temurin:17-jre-focal

# Set the working directory
WORKDIR /app

# Copy ONLY the compiled .jar file from the 'builder' stage
# IMPORTANT: Make sure this filename matches the one in your 'target' folder
COPY --from=builder /app/target/moneymaster-0.0.1-SNAPSHOT.jar .

# Expose the port that your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java", "-jar", "moneymaster-0.0.1-SNAPSHOT.jar"]