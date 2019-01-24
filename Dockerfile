FROM openjdk:11-jdk-oracle

# VOLUME /tmp

COPY . app

WORKDIR app

RUN ./mvnw package

ARG JAR_FILE=ml-artist-facebook-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","target/${JAR_FILE}"]