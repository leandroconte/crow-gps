
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project
val kafka_api_version: String by project
val koin_version: String by project
val koin_annotations: String by project
val koin_ksp_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.7"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"

    // Apply the application plugin to add support for building a CLI application in Java.
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("application")
}

group = "br.com.leandroconte"
version = "0.0.1"

application {
    mainClass.set("br.com.leandroconte.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("org.apache.kafka:kafka-clients:$kafka_api_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.7")
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
}
