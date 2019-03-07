FROM openjdk:8 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY . $APP_HOME
#COPY src $APP_HOME/src
#COPY gradle $APP_HOME/gradle
RUN ./gradlew build

FROM openjdk:8-jre-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/client.jar .
ENTRYPOINT ["/usr/bin/java","-jar", "client.jar"]