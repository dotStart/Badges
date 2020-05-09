FROM adoptopenjdk:11-jdk-hotspot AS build

RUN apt-get update && \
    apt-get install -y maven && \
    mkdir -p /usr/src/badges

ADD pom.xml /usr/src/badges
ADD src /usr/src/badges/

FROM adoptopenjdk:11-jre-hotspot

RUN mkdir -p /opt/badges && \
    useradd -MU -d /opt/badges badges && \
    chown -R badges:badges /opt/badges
COPY --from=build /usr/src/badges/target/Badge.jar /opt/badges/Badge.jar

USER badges
WORKDIR /opt/badges
ENTRYPOINT ["java", "-jar", "/opt/badges/Badge.jar"]
