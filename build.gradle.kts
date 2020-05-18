import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.70"
    kotlin("kapt") version "1.3.70"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")

    implementation("io.micronaut:micronaut-runtime:1.3.5")
    implementation("io.micronaut:micronaut-http-server-netty:1.3.5")
    implementation("io.micronaut:micronaut-views-thymeleaf:1.3.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")

    kapt("io.micronaut:micronaut-inject-java:1.3.5")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        javaParameters = true
    }
}

application {
    mainClassName = "hello.WebAppKt"
}

tasks.register<DefaultTask>("stage") {
    dependsOn("installDist")
}
