FROM openjdk:latest
COPY target/SoftEngGroup2-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "SoftEngGroup2-1.0-SNAPSHOT-jar-with-dependencies.jar"]