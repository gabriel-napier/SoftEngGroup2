# openjdk container to run java application
FROM openjdk:latest
# copy jar into container
COPY target/App-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/
# copy queries to docker volume
COPY Query/* /tmp/
# set working directory for application
WORKDIR /tmp
# define entrypoint to run java application
ENTRYPOINT ["java", "-jar", "App-1.0-SNAPSHOT-jar-with-dependencies.jar"]