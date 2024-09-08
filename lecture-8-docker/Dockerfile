# Stage 1: Build the application
FROM gradle:jdk17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the gradle wrapper and the build.gradle files first to optimize the Docker cache
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# Download dependencies
RUN ./gradlew build --no-daemon --parallel --build-cache -x test --info --stacktrace

# Copy the rest of the source code
COPY . .

# Build the application
RUN ./gradlew build -x test --no-daemon --info --stacktrace

# Stage 2: Run the application
FROM gradle:jdk17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/example-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]