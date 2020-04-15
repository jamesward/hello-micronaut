FROM oracle/graalvm-ce:20.0.0 as builder

WORKDIR /app
COPY . /app

RUN gu install native-image

RUN ./gradlew -i --no-daemon --console=plain distTar
RUN tar -xvf build/distributions/hello-micronaut.tar -C build/distributions
RUN native-image --static --no-fallback --no-server \
      -H:IncludeResources=logback.xml -H:IncludeResources=application.properties -H:IncludeResources=assets/.* -H:IncludeResources=views/.* \
      -H:Name=hello-micronaut \
      -cp /app/build/distributions/hello-micronaut/lib/hello-micronaut.jar:/app/build/distributions/hello-micronaut/lib/* \
      hello.WebAppKt

FROM scratch

COPY --from=builder /app/hello-micronaut /app

CMD ["/app"]
