plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("kotlin-kapt")
}

android {
  compileSdk = AppConfig.compileSdk

  buildFeatures {
    dataBinding = true
  }

  defaultConfig {
    minSdk = AppConfig.minSdk
    targetSdk = AppConfig.targetSdk
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = "11"
  }

  lintOptions {
    isAbortOnError = false
  }

  packagingOptions {
    exclude("META-INF/DEPENDENCIES")
    exclude("META-INF/LICENSE")
    exclude("META-INF/LICENSE.txt")
    exclude("META-INF/license.txt")
    exclude("META-INF/NOTICE")
    exclude("META-INF/NOTICE.txt")
    exclude("META-INF/notice.txt")
    exclude("META-INF/ASL2.0")
    exclude("META-INF/*.kotlin_module")
  }
}

dependencies {
  implementationProject(ModuleDependencies.libraries)
  implementation(AppDependencies.androidLibraries)
  implementation(AppDependencies.networkLibraries)
  implementation(AppDependencies.datastoreLibraries)
  implementation(AppDependencies.dependencyInjectionLibraries)

  kapt(AppDependencies.kaptLibraries)

  testImplementation(AppDependencies.testLibraries)
  androidTestImplementation(AppDependencies.androidTestLibraries)
}