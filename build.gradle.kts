plugins {
    kotlin("jvm") version "2.1.0"
    id("application")
    `maven-publish`

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
    implementation("org.joml:joml:1.10.8")
    implementation("dev.ng5m:serialization:1.0-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.24.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.24.0")

    implementation("de.articdive:jnoise-pipeline:4.1.0")
    implementation("org.openjdk.jol:jol-core:0.10")

    implementation(group = "it.unimi.dsi", name = "fastutil", version = "8.2.2")

    implementation("org.graalvm.polyglot:polyglot:25.0.1")
    implementation("org.graalvm.polyglot:js:25.0.1")

    implementation("com.github.luben:zstd-jni:1.5.7-6")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")

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

application {
    mainClass = "dev.ng5m.MainKt"
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    add("archives", sourcesJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}