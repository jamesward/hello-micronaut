plugins {
    application
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.micronaut:micronaut-runtime:1.3.4")
    implementation("io.micronaut:micronaut-http-server-netty:1.3.4")
    implementation("io.micronaut:micronaut-views-thymeleaf:1.3.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    kapt("io.micronaut:micronaut-inject-java:1.3.4")
    kapt("io.micronaut:micronaut-graal:1.3.4")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        javaParameters = true
    }
}

application {
    mainClassName = "hello.WebAppKt"
}
