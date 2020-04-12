FROM openjdk:8-jdk-alpine
RUN addgroup -S health && adduser -S health -G health
USER health:health
ARG JAR_FILE=build.libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8000
