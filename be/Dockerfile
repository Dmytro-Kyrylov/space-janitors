# the first stage of our build will use a maven 3.6.1 parent image
FROM gradle:7.2.0-jdk11 AS GRADLE_BUILD
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# copy the pom and src code to the container
COPY . ./

# package our application code
RUN gradle clean bootJar --no-daemon

# the second stage of our build will use open jdk 8 on alpine 3.9
FROM openjdk:11-jre-slim
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=GRADLE_BUILD $APP_HOME/build/libs/*.jar /sj-be.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/sj-be.jar"]
