FROM gradle:latest AS build
COPY . /usr/app
WORKDIR /usr/app

RUN gradle build --no-daemon

FROM openjdk:8

EXPOSE 8080

COPY --from=build /usr/app/build/libs/project-one-1.0-SNAPSHOT.jar project-one.jar
CMD ["java", "-jar", "project-one.jar"]
