plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("kapt") version "2.1.0"

    java

    kotlin("plugin.lombok") version "1.8.10"
    id("io.freefair.lombok") version "5.3.0"
}

group = "dev.ng5m"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(kotlin("reflect"))

    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.slf4j:slf4j-simple:2.0.17")

    implementation("com.google.code.gson:gson:2.13.1")
    implementation("io.netty:netty-all:4.2.5.Final")
    implementation("io.ktor:ktor-network:3.3.0")


    implementation("dev.ng5m:serialization:1.0-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.24.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.24.0")

    implementation("de.articdive:jnoise-pipeline:4.1.0")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.projectlombok:lombok:1.18.30")

    kapt("org.projectlombok:lombok:1.18.30")

}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }

        kotlin {
            srcDirs("src/main/kotlin")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

kapt {
    keepJavacAnnotationProcessors = true
}