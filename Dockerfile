FROM gradle:9.0.0-jdk21 AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home
COPY *.gradle.kts gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
WORKDIR /home/gradle/app
RUN gradle dependencies -i --stacktrace

FROM gradle:9.0.0-jdk21 AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean jvmJar --no-daemon

FROM openjdk:21 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/darkladyblog.jar
ENTRYPOINT ["java","-jar","/app/darkladyblog.jar"]