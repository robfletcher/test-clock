plugins {
    kotlin("jvm") version "1.4.21"
}
repositories {
    mavenCentral()
}

val kotestVersion: String by project

dependencies {
    implementation(project(":test-clock-core"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
