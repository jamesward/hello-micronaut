# Hello, Micronaut

Run Locally:
```
./gradlew -t run
```

Visit: [http://localhost:8080](http://localhost:8080)

Containerize Locally as a GraalVM native image:
```
docker build -t hello-micronaut .
```

Run container:
```
docker run -p8080:8080 hello-micronaut
```

Deploy:

[![Run on Google Cloud](https://deploy.cloud.run/button.svg)](https://deploy.cloud.run)
