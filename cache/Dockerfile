FROM openjdk:13-alpine
RUN addgroup -S cacheapp && adduser -S cacheapp -G cacheapp
USER cacheapp:cacheapp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cacheapp.jar
ENTRYPOINT ["java","-jar","-Dcom.sun.management.jmxremote","-Dcom.sun.management.jmxremote.authenticate=false","-Dcom.sun.management.jmxremote.ssl=false","-Dcom.sun.management.jmxremote.local.only=false","-Dcom.sun.management.jmxremote.port=1099","-Dcom.sun.management.jmxremote.rmi.port=1099","-Djava.rmi.server.hostname=127.0.0.1","/cacheapp.jar"]
EXPOSE 8000
