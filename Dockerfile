FROM adoptopenjdk:11-jdk-hotspot AS build

RUN apt-get update && \
    apt-get install -y maven && \
    mkdir -p /usr/src/badges

ADD pom.xml /usr/src/badges
ADD src /usr/src/badges/

WORKDIR /usr/src/badges
RUN mvn clean package

FROM adoptopenjdk:11-jre-hotspot

RUN mkdir -p /opt/badges && \
    useradd -MU -d /opt/badges badges && \
    chown -R badges:badges /opt/badges
COPY --from=build /usr/src/badges/target/badges.jar /opt/badges/badges.jar

USER badges
WORKDIR /opt/badges
ENTRYPOINT ["java", "-jar", "/opt/badges/badges.jar"]
