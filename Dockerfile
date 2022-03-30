FROM gradle:latest AS build
COPY . /home/gradle/app
WORKDIR /home/gradle/app

RUN gradle build --no-daemon
RUN pwd

FROM openjdk:8

EXPOSE 8080

COPY --from=build /home/gradle/app/build/libs/project-one-1.0-SNAPSHOT.jar project-one.jar
CMD ["java", "-jar", "project-one.jar"]
