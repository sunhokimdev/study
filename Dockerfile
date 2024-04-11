FROM amazoncorretto:8
ARG JAR_PATH=./build/libs/study-0.0.1-SNAPSHOT.jar
EXPOSE 9999 8080
COPY ${JAR_PATH} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]