plugins {
    kotlin("jvm") version "2.1.0"

    java
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