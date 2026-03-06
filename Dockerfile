# Use official JDK image
FROM openjdk:22-jdk-slim



# Set working dir
WORKDIR /app

# Copy jar file from Maven build
COPY target/MEDICINE-0.0.1-SNAPSHOT.jar app.jar

#FOR Copy differnt method
# ADD target/MEDICINE-0.0.1-SNAPSHOT.jar app.jar

#Expose
Expose 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
