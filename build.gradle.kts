import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    id("com.google.cloud.tools.jib") version "1.0.2"
    id("com.github.johnrengelman.shadow") version "4.0.4"
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

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")
    runtimeOnly("org.thymeleaf:thymeleaf:3.0.11.RELEASE")

    kapt("io.micronaut:micronaut-inject-java:1.2.7")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        javaParameters = true
    }
}

application {
    mainClassName = "hello.WebAppKt"
}

jib {
    val projectId: String? by project
    val repoName: String? by project

    to.image = "gcr.io/$projectId/$repoName"
    container {
        mainClass = application.mainClassName
        ports = listOf("8080")
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks.register<DefaultTask>("stage") {
    dependsOn("shadowJar")
}
