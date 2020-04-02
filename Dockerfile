FROM adoptopenjdk/openjdk11-openj9:x86_64-alpine-jdk-11.0.5_10_openj9-0.17.0-slim
ENV LANG C.UTF-8
COPY ./target/*.jar ./app.jar
EXPOSE 8008
ENTRYPOINT ["java","-jar","/app.jar"]