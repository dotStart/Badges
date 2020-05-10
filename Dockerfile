FROM adoptopenjdk:11-jre-hotspot

RUN mkdir -p /opt/badges && \
    useradd -MU -d /opt/badges badges && \
    chown -R badges:badges /opt/badges
ADD target/badges.jar /opt/badges/badges.jar

USER badges
WORKDIR /opt/badges
ENTRYPOINT ["java", "-jar", "/opt/badges/badges.jar"]
