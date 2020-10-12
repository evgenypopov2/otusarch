FROM openjdk:8-jdk-alpine
RUN addgroup -S clientservice && adduser -S clientservice -G clientservice
USER clientservice:clientservice
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8000
