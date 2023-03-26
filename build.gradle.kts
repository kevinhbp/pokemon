// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
  }

  dependencies {
    classpath(AppDependencies.androidGradle)
    classpath(AppDependencies.kotlinGradlePlugin)
    classpath(AppDependencies.navigationGradlePlugin)
    classpath(AppDependencies.googleService)
    classpath(AppDependencies.crashlyticsGradle)
  }
}

plugins {
  id("com.diffplug.spotless") version "5.2.0"
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven {
      setUrl("https://jitpack.io")
    }
    maven {
      setUrl(project.property("artifactory_url") as String? ?: "")
      credentials {
        username = project.property("artifactory_username") as String?
        password = project.property("artifactory_password") as String?
      }
    }
  }
}

spotless {
  kotlin {
    target("**/*.kt")
    ktlint(Versions.kotlinVersion).userData(mapOf("max_line_length" to "100"))
  }
}