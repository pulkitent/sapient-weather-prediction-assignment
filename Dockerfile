FROM adoptopenjdk:11-jdk-hotspot AS builder

WORKDIR /app

# Copy Gradle Wrapper files
COPY gradlew .
COPY gradle gradle

# Copy project configuration files
COPY settings.gradle .
COPY build.gradle .

# Download and cache Gradle dependencies
RUN ./gradlew dependencies

# Copy project source code
COPY src ./src

# Build the application
RUN ./gradlew build

# Final image
FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/weather-prediction-service-0.0.1.jar .

# Run the application
CMD ["java", "-jar", "weather-prediction-service-0.0.1.jar"]