FROM openjdk:12-ea-25-jdk-alpine3.8

ENV VERTICLE_FILE target/HazelCast-Receiver-0.0.1-SNAPSHOT.jar

RUN mkdir -p /opt/verticles

COPY $VERTICLE_FILE /opt/verticles

ENTRYPOINT ["java", "-jar", "/opt/verticles/HazelCast-Receiver-0.0.1-SNAPSHOT.jar","-cluster"]
