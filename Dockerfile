ARG PAYARA_VERSION=6.2024.4-jdk17
ARG POSTGRES_VERSION=42.7.3

FROM maven:3.9.9-eclipse-temurin-17 AS build
ARG POSTGRES_VERSION
WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B package

FROM payara/micro:${PAYARA_VERSION}
ARG POSTGRES_VERSION
USER root
WORKDIR /opt/payara
RUN mkdir -p /opt/payara/libs

COPY --from=build /workspace/target/academy.war /opt/payara/app.war
COPY --from=build /root/.m2/repository/org/postgresql/postgresql/${POSTGRES_VERSION}/postgresql-${POSTGRES_VERSION}.jar /opt/payara/libs/postgresql.jar
COPY docker/payara/run.sh /opt/payara/run.sh

RUN chmod +x /opt/payara/run.sh && chown -R payara:payara /opt/payara

USER payara
EXPOSE 8080
ENTRYPOINT ["/bin/sh", "/opt/payara/run.sh"]
