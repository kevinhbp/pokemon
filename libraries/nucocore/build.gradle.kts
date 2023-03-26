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
    vectorDrawables.useSupportLibrary = true

    buildConfigField("String", "URL_PRO", "\"${AppConfig.BASE_URL_PRO}\"")
    buildConfigField("String", "URL_STG", "\"${AppConfig.BASE_URL_STG}\"")

    resValue("string", "app_name", AppConfig.appName)
  }

  buildTypes {
    debug {
      buildConfigField("String", "VERSION_NAME", "\"${AppConfig.versionName}-dev\"")
      buildConfigField("String", "VERSION_CODE", "\"${AppConfig.versionCode}\"")

      isMinifyEnabled = false
      resValue("string", "app_name", "${AppConfig.appName}-dev")
    }
    release {
      buildConfigField("String", "VERSION_NAME", "\"${AppConfig.versionName}\"")
      buildConfigField("String", "VERSION_CODE", "\"${AppConfig.versionCode}\"")

      isMinifyEnabled = true
//      signingConfig = signingConfigs.getByName("release")
      resValue("string", "app_name", AppConfig.appName)
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
  implementation(AppDependencies.androidLibraries)
  implementation(AppDependencies.dependencyInjectionLibraries)
  implementation(AppDependencies.networkLibraries)
  implementation(AppDependencies.persistenceLibraries)
  implementation(AppDependencies.navigationLibraries)

  kapt(AppDependencies.kaptLibraries)

  testImplementation(AppDependencies.testLibraries)
  androidTestImplementation(AppDependencies.androidTestLibraries)
}