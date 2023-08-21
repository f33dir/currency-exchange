import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val postgres_version: String by project
val h2_version: String by project

val dbUrl: String? = System.getenv("DATABASE_URL")
val dbName: String? = System.getenv("DATABASE_USER")
val dbPassword: String? = System.getenv("DATABASE_PASSWORD")

plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("com.github.johnrengelman.shadow") version  "7.1.1"
    id("io.ktor.plugin") version "2.3.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    id("org.liquibase.gradle") version "2.2.0"
}

group = "com.f33dir"
version = "0.0.1"

application {
    mainClass.set("com.f33dir.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}



repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
    gradlePluginPortal()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.31")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")
    implementation("io.ktor:ktor-server-freemarker-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-resources-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.h2database:h2:$h2_version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.3")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("io.insert-koin:koin-ktor:3.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
    liquibaseRuntime("ch.qos.logback:logback-core:1.2.9")
    liquibaseRuntime("ch.qos.logback:logback-classic:1.2.9")
    liquibaseRuntime("org.liquibase:liquibase-core:4.16.1")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.2")
    liquibaseRuntime("info.picocli:picocli:4.6.1")
    liquibaseRuntime("javax.xml.bind:jaxb-api:2.2.4")
    liquibaseRuntime("org.postgresql:postgresql:$postgres_version")
    testImplementation("org.mockito:mockito-core:5.4.0")

}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed","skipped","failed")
    }
}
liquibase {
    activities.register("main"){
        this.arguments = mapOf(
            "logLevel" to "info",
            "changeLogFile" to "src/main/resources/db/migration/Changelog.sql",
            "url" to dbUrl,
            "username" to dbName,
            "password" to dbPassword,
            "driver" to "org.postgresql.Driver"
        )
    }
}


