FROM adoptopenjdk/openjdk8 as builder

WORKDIR /app
COPY . /app

RUN ./gradlew --no-daemon stage

FROM adoptopenjdk/openjdk8:jre

COPY --from=builder /app/build/libs/hello-micronaut.jar /hello-micronaut.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/hello-micronaut.jar"]
