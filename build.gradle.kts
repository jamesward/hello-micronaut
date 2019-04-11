import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension

plugins {
    application
    kotlin("jvm") version "1.3.21"
    kotlin("kapt") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.21"
    id("com.google.cloud.tools.jib") version "1.0.2"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))

    compile("io.micronaut:micronaut-runtime:1.1.0")
    compile("io.micronaut:micronaut-http-client:1.1.0")
    compile("io.micronaut:micronaut-http-server-netty:1.1.0")
    compile("io.micronaut:micronaut-views:1.1.0")
    compile("ch.qos.logback:logback-classic:1.2.3")

    runtime("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")
    runtime("org.thymeleaf:thymeleaf:3.0.11.RELEASE")

    kapt("io.micronaut:micronaut-inject-java:1.1.0")
    kapt("io.micronaut:micronaut-validation:1.1.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.21")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.1")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.1")

    kaptTest("io.micronaut:micronaut-inject-java:1.1.0")
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

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

configure<AllOpenExtension> {
    annotation("io.micronaut.aop.Around")
}

tasks.all {
    when(this) {
        is JavaForkOptions -> {
            jvmArgs("-noverify")
            jvmArgs("-XX:TieredStopAtLevel=1")
        }
    }
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

tasks.create("stage") {
    dependsOn("shadowJar")
}
