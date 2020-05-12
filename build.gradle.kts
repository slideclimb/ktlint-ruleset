group = "nl.deltadak"
version = "0.1"

plugins {

    val kotlinVersion = "1.3.72"

    application
    kotlin("jvm") version kotlinVersion
    java // Required by at least JUnit.
}

dependencies {
    implementation(kotlin("stdlib"))
    // To "prevent strange errors".
    implementation(kotlin("reflect"))
    // Kotlin reflection.
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))

    implementation("com.pinterest.ktlint:ktlint-core:0.36.0")
    implementation("com.pinterest.ktlint:ktlint-test:0.36.0")

    val kotestVersion = "4.0.5"
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion") // for kotest property test
//    // JUnit 5
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
//    testRuntimeOnly("org.junit.platform:junit-platform-console:1.6.2")
//
//    // Kotlintest
//    testImplementation("io.kotlintest:kotlintest-core:3.4.2")
//    testImplementation("io.kotlintest:kotlintest-assertions:3.4.2")
//    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")

}

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
