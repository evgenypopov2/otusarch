FROM openjdk:8-jdk-alpine
RUN addgroup -S authapp && adduser -S authapp -G authapp
USER authapp:authapp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} authapp.jar
ENTRYPOINT ["java","-jar","/authapp.jar"]
EXPOSE 8080
