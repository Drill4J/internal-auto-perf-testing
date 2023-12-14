plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.epam.auto.perf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation( group=  "ch.qos.logback", name ="logback-classic", version = "1.2.9")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}