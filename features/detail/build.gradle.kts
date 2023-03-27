plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("kotlin-kapt")
  id("androidx.navigation.safeargs.kotlin")
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

  buildTypes {
    debug {
      isMinifyEnabled = false
    }
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = "11"
  }
}

dependencies {
  implementationProject(ModuleDependencies.libraries)
  implementationProject(ModuleDependencies.session)

  implementation(AppDependencies.androidLibraries)
  implementation(AppDependencies.dependencyInjectionLibraries)
  implementation(AppDependencies.networkLibraries)
  implementation(AppDependencies.navigationLibraries)

  kapt(AppDependencies.kaptLibraries)
  testImplementation(AppDependencies.testLibraries)
  androidTestImplementation(AppDependencies.androidTestLibraries)
}