FROM openjdk:10-jdk AS build
ARG ref=master

RUN apt-get update && \
    apt-get install -y maven && \
    git clone https://github.com/dotStart/Badges /usr/src/badges && \
    cd /usr/src/badges && \
    git checkout $ref && \
    mvn clean package

FROM openjdk:10-slim

RUN mkdir -p /opt/badges && \
    useradd -MU -d /opt/badges badges && \
    chown -R badges:badges /opt/badges
COPY --from=build /usr/src/badges/target/Badge.jar /opt/badges/Badge.jar

USER badges
WORKDIR /opt/badges
ENTRYPOINT ["java", "-jar", "/opt/badges/Badge.jar"]
