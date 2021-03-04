plugins {
    kotlin("jvm") version "1.4.31"
    kotlin("kapt") version "1.4.31"
    kotlin("plugin.allopen") version "1.4.31"
    id("io.micronaut.application") version "1.4.0"
}

repositories {
    mavenCentral()
}

micronaut {
    version.set("2.3.4")
    runtime("netty")
    processing {
        incremental(true)
        annotations("hello.*")
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
}

tasks.withType<io.micronaut.gradle.graalvm.NativeImageTask> {
    args("--verbose")
    args("--install-exit-handlers")
    args("--static")
    args("--libc=musl")
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
    mainClass.set("hello.WebAppKt")
}
