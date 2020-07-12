FROM openjdk:13-alpine
RUN addgroup -S cacheapp && adduser -S cacheapp -G cacheapp
USER cacheapp:cacheapp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cacheapp.jar
ENTRYPOINT ["java","-jar","/cacheapp.jar"]
EXPOSE 8000
