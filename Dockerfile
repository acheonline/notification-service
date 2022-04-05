FROM amazoncorretto:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} billing-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/billing-service-0.0.1-SNAPSHOT.jar"]