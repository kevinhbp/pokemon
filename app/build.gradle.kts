plugins {
  id("com.android.application")
  id("com.google.gms.google-services")
  id("com.google.firebase.crashlytics")
  id("kotlin-android")
  id("kotlin-android-extensions")
  id("kotlin-kapt")
  id("androidx.navigation.safeargs.kotlin")
}

android {
  compileSdk = AppConfig.compileSdk
  buildToolsVersion = AppConfig.buildToolsVersion

  buildFeatures {
    dataBinding = true
  }

  defaultConfig {
    applicationId = AppConfig.appId
    minSdk = AppConfig.minSdk
    targetSdk = AppConfig.targetSdk
    versionCode = AppConfig.versionCode
    versionName = AppConfig.versionName
    vectorDrawables.useSupportLibrary = true
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    setProperty("archivesBaseName", AppConfig.getApkFileName())
  }

  packagingOptions {
    resources.excludes.add("META-INF/*")
    jniLibs {
      pickFirsts.add("**/*.so")
    }
  }

  signingConfigs {
    create("release") {
      storeFile = file("../../nursery.key.jks")
      keyAlias = "rndnursery"
      storePassword = "7i0@Frn41"
      keyPassword = "7i0@Frn41"
    }
  }

  buildTypes {
    debug {
      isDebuggable = true
      isMinifyEnabled = false
      versionNameSuffix = "-dev"
      resValue("string", "app_name", "${AppConfig.appName}-dev")
    }
    release {
      isDebuggable = false
      isMinifyEnabled = true
//      signingConfig = signingConfigs.getByName("release")
      resValue("string", "app_name", AppConfig.appName)
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

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "11"
      freeCompilerArgs = freeCompilerArgs + "-Xallow-jvm-ir-dependencies"
      freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
      freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview"
    }
  }

  lint {
    checkReleaseBuilds = false
    // Or, if you prefer, you can continue to check for errors in release builds,
    // but continue the build even when errors are found:
    abortOnError = false
  }

  splits {
    abi {
      isEnable = true
      reset()
      include("armeabi-v7a")
      isUniversalApk = false
    }
  }
}

subprojects {
  project.configurations.all {
    resolutionStrategy.eachDependency {
      if (requested.group == "androidx.core" &&
        !requested.name.contains("androidx")
      ) {
        useVersion(Versions.appcompat)
      }
    }
    resolutionStrategy.force("org.antlr:antlr4-runtime:4.7.1")
    resolutionStrategy.force("org.antlr:antlr4-tool:4.7.1")
  }
}

dependencies {
  implementationProject(ModuleDependencies.libraries)
  implementationProject(ModuleDependencies.session)
  implementationProject(ModuleDependencies.features)

  implementation(AppDependencies.androidLibraries)
  implementation(AppDependencies.dependencyInjectionLibraries)
  implementation(AppDependencies.networkLibraries)
  implementation(AppDependencies.persistenceLibraries)
  implementation(AppDependencies.navigationLibraries)

  implementationPlatform(AppDependencies.firebasePlatformLibraries)
  implementation(AppDependencies.firebaseLibraries)

  kapt(AppDependencies.kaptLibraries)
  androidTestImplementation(AppDependencies.androidTestLibraries)
}