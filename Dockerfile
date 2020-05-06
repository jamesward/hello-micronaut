FROM oracle/graalvm-ce:20.0.0-java11 as builder

WORKDIR /app
COPY . /app

RUN gu install native-image

RUN ./gradlew --no-daemon --console=plain distTar
RUN tar -xvf build/distributions/hello-micronaut.tar -C build/distributions

RUN curl -L -o musl.tar.gz https://github.com/gradinac/musl-bundle-example/releases/download/v1.0/musl.tar.gz && \
    tar -xvzf musl.tar.gz

RUN native-image --static --no-fallback --no-server --enable-http --enable-https \
      -H:IncludeResources=logback.xml -H:IncludeResources=application.properties -H:IncludeResources=assets/.* -H:IncludeResources=views/.* \
      -H:Name=hello-micronaut \
      -H:UseMuslC=/app/bundle/ \
      -cp /app/build/distributions/hello-micronaut/lib/hello-micronaut.jar:/app/build/distributions/hello-micronaut/lib/* \
      hello.WebAppKt

FROM scratch

COPY --from=builder /app/hello-micronaut /app

CMD ["/app"]
