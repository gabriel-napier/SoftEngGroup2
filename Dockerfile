# openjdk container to run java application
FROM openjdk:latest
# copy jar into container
COPY target/com.napier.sem-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/
# copy queries to docker volume
COPY Query/* /tmp/
# set working directory for application
WORKDIR /tmp
# define entrypoint to run java application
ENTRYPOINT ["java", "-jar", "com.napier.sem-1.0-SNAPSHOT-jar-with-dependencies.jar"]