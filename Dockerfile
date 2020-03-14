# openjdk container to run java application
FROM openjdk:latest
# copy jar into container
COPY target/SoftEngGroup2-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/
# copy queries to docker volume
COPY Query/* /mydata/data/
# set working directory for application
WORKDIR /tmp
# define entrypoint to run java application
ENTRYPOINT ["java", "-jar", "SoftEngGroup2-1.0-SNAPSHOT-jar-with-dependencies.jar"]