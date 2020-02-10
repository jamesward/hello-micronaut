FROM adoptopenjdk/openjdk8 as builder

WORKDIR /app
COPY .  /app

RUN ./gradlew stage

FROM adoptopenjdk/openjdk8

COPY --from=builder /app/build/distributions/hello-micronaut /app

CMD ["/app/bin/hello-micronaut"]

