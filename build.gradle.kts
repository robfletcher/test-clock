plugins {
  java
}

subprojects {

  apply(plugin = "java")

  repositories {
    mavenCentral()
    jcenter()
  }

  val junit5Version: String by project

  dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

