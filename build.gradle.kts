plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    kotlin("plugin.allopen") version "1.6.10"
    id("io.micronaut.application") version "3.1.1"
}

repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    version.set("3.1.1")
    processing {
        incremental(true)
        annotations("hello.*")
    }
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.micronaut:micronaut-validation")

    compileOnly("org.graalvm.nativeimage:svm")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
}

graalvmNative {
    binaries {
        named("main") {
            verbose.set(true)
            buildArgs.addAll(
                "--static",
                "--libc=musl",
                "--install-exit-handlers"
            )
            // To build a mostly static image you can use this. It *should* work on anything with a libc.
            // buildArgs.addAll("-H:+StaticExecutableWithDynamicLibC", "--install-exit-handlers")
        }
    }
    toolchainDetection.set(false)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks {

    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

application {
    mainClass.set("hello.WebAppKt")
}
