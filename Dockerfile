# Use the official OpenJDK image as the base image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app


# Copy the JAR file into the container
COPY target/*.jar app.jar

# Expose the port (must match your application.properties port)
EXPOSE 8090

# Run the JAR file
CMD ["java", "-jar", "app.jar"]

#docker build  -t DockerHubName/ImgName:v1 .
#docker run imagename
