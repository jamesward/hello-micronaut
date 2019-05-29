FROM adoptopenjdk/openjdk8 as builder

WORKDIR /app
COPY . /app

RUN ./gradlew --no-daemon stage

FROM adoptopenjdk/openjdk8:jre

COPY --from=builder /app/server/build/libs/server.jar /server.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/server.jar"]
