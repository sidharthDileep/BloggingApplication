FROM openjdk:8
EXPOSE 8888
ADD target/config-server.jar config-server.jar
ENTRYPOINT ["java", "-jar", "/config-server.jar"]