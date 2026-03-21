# Use Maven image to build the application
FROM maven:latest

# Set working directory
WORKDIR /app

# Copy pom.xml first
COPY pom.xml /app/

# Copy all files
COPY . /app/

# Build the project
RUN mvn clean package

# Run the app
CMD ["java", "-jar", "target/shopping-cart-1.0-SNAPSHOT.jar"]