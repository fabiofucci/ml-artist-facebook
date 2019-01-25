# In questo momento la compilazione fallisce con openjdk:8-jdk-alpine
# FROM openjdk:8-jdk-alpine
FROM openjdk:11-jdk-oracle

# VOLUME /tmp

COPY . app

WORKDIR app

RUN sh mvnw package

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/ml-artist-facebook-0.0.1-SNAPSHOT.jar"]