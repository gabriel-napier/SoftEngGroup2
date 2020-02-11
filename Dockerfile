# openjdk container to run java application
FROM openjdk:latest
# copy jar into container
COPY target/SoftEngGroup2-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/
COPY Query/* /tmp/
WORKDIR /tmp
# define entrypoint to run java application
ENTRYPOINT ["java", "-jar", "SoftEngGroup2-1.0-SNAPSHOT-jar-with-dependencies.jar"]