FROM openjdk:8-jdk-alpine

RUN mkdir -p /opt/apps

COPY ./target/spring-boot-prometheus-1.0.0.jar /opt/apps/

ENTRYPOINT ["java"]

CMD ["-jar", "/opt/apps/spring-boot-prometheus-1.0.0.jar"]

