import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nl.deltadak"
version = "0.2"

plugins {

    val kotlinVersion = "1.4.21"

    application
    kotlin("jvm") version kotlinVersion
    java // Required by at least JUnit.

    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("stdlib"))
    // To "prevent strange errors".
    implementation(kotlin("reflect"))
    // Kotlin reflection.
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))

    val ktlintVersion = "0.40.0"
    implementation("com.pinterest.ktlint:ktlint-core:$ktlintVersion")
    implementation("com.pinterest.ktlint:ktlint-test:$ktlintVersion")

    val kotestVersion = "4.3.2"
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion") // for kotest property test

    ktlintRuleset(files("lib/ktlint-ruleset-0.2.jar"))
}

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}