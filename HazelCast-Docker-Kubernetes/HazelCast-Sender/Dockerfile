FROM openjdk:12-ea-25-jdk-alpine3.8

ENV VERTICLE_FILE target/HazelCast-Sender-0.0.1-SNAPSHOT-fat.jar

RUN mkdir -p /opt/verticles

COPY $VERTICLE_FILE /opt/verticles

ENTRYPOINT ["java", "-jar", "/opt/verticles/HazelCast-Sender-0.0.1-SNAPSHOT-fat.jar","-cluster"]
