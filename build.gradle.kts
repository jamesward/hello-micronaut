plugins {
    application
    kotlin("jvm") version "1.3.61"
    kotlin("kapt") version "1.3.61"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.micronaut:micronaut-runtime:1.2.7")
    implementation("io.micronaut:micronaut-http-server-netty:1.2.7")
    implementation("io.micronaut:micronaut-views:1.2.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    runtimeOnly("org.thymeleaf:thymeleaf:3.0.11.RELEASE")

    kapt("io.micronaut:micronaut-inject-java:1.2.7")
    kapt("io.micronaut:micronaut-graal:1.2.7")
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
