FROM eclipse-temurin:21
LABEL maintainer="lucasbo.dev@gmail.com"
WORKDIR /parkable
COPY target/Parkable-0.0.1-SNAPSHOT.jar parkable.jar
ENTRYPOINT ["java", "-jar", "parkable.jar"]