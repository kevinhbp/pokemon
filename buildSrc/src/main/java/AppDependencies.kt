import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object AppDependencies {
  const val androidGradle =
    "com.android.tools.build:gradle:${Versions.androidGradleVersion}"
  const val kotlinGradlePlugin =
    "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
  const val navigationGradlePlugin =
    "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgsNavigationVersion}"

  const val googleService = "com.google.gms:google-services:${Versions.googleService}"
  const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradle}"

  //std lib
  private const val kotlinStdLib =
    "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"

  //android ui
  const val googlePlayCore = "com.google.android.play:core:${Versions.googlePlayCore}"
  private const val material = "com.google.android.material:material:${Versions.material}"
  private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
  private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
  private const val lifecycleKtx =
    "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
  private const val viewModelKtx =
    "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
  private const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
  private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle}"
  private const val constraintLayout =
    "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
  private const val multiDex = "androidx.multidex:multidex:${Versions.multidexVersion}"
  private const val swipeRefreshVersion =
    "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshVersion}"

  //retrofit
  private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  private const val okHttpInterceptor =
    "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpInterceptor}"

  // room
  private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
  private const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

  //coroutines
  private const val coroutineCore =
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
  private const val coroutineAndroid =
    "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
  private const val coroutineTest =
    "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"
  private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
  private const val liveDataKtx =
    "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

  // jetpack
  private const val navigationRuntime =
    "androidx.navigation:navigation-runtime-ktx:${Versions.jetpackNavigation}"
  private const val navigationFragment =
    "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNavigation}"
  private const val navigationDynamicFeatures =
    "androidx.navigation:navigation-dynamic-features-fragment:${Versions.jetpackNavigation}"
  private const val navigationUiKtx =
    "androidx.navigation:navigation-ui-ktx:${Versions.jetpackNavigation}"

  // chucker
  private const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"

  // database encrypt
  private const val sqlchipper = "net.zetetic:android-database-sqlcipher:${Versions.sqlchipper}"

  // shimmer
  private const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

  // glide
  private const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

  // alert
  private const val alerter = "com.github.tapadoo:alerter:${Versions.alerter}"

  // moshi
  const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
  private const val moshiRetrofitFactory =
    "com.squareup.retrofit2:converter-moshi:${Versions.moshiRetrofitFactoryVersion}"
  private const val gsonRetrofitFactory =
    "com.squareup.retrofit2:converter-gson:${Versions.gsonRetrofitFactoryVersion}"
  private const val moshiCodegen =
    "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"

  // material color
  private const val materialColor =
    "com.github.mcginty:material-colors:${Versions.materialColorVersion}"

  // sDp size unit
  private const val sDp = "com.intuit.sdp:sdp-android:${Versions.spDpVersion}"
  private const val sSp = "com.intuit.ssp:ssp-android:${Versions.spDpVersion}"

  // preferences
  private const val jetpackDatastore =
    "androidx.datastore:datastore-preferences:${Versions.jetpackDataStoreVersion}"
  private const val jetpackDatastoreCore =
    "androidx.datastore:datastore:${Versions.jetpackDataStoreVersion}"

  // Material Dialog
  private const val materialDialog =
    "com.afollestad.material-dialogs:core:${Versions.materialDialog}"

  // Lottie
  private const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

  // toasty
  const val toasty = "com.github.GrenderG:Toasty:${Versions.toastyVersion}"

  // chart view
  const val chartView = "com.github.PhilJay:MPAndroidChart:${Versions.chartViewVersion}"

  const val pageIndicatorView = "com.github.zhpanvip:viewpagerindicator:${Versions.pageIndicator}"

  const val photoView = "com.github.chrisbanes:PhotoView:${Versions.photoView}"

  const val timelineView = "com.github.vipulasri:timelineview:${Versions.timelineViewVersion}"

  private const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.pagingVersion}"

  // Koin
  private const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
  private const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
  private const val koinTest = "io.insert-koin:koin-test:${Versions.koin}"

  // Firebase
  private const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
  private const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
  private const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"

  // test libs
  private const val junit = "junit:junit:${Versions.junit}"
  private const val mockk = "io.mockk:mockk:${Versions.mockk}"
  private const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
  private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"

  // instrument test libs
  private const val truth = "com.google.truth:truth:${Versions.truthVersion}"
  private const val archCoreTesting =
    "androidx.arch.core:core-testing:${Versions.archCoreVersion}"
  private const val espressoContrib =
    "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
  private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
  private const val espressoIntent =
    "androidx.test.espresso:espresso-intents:${Versions.espresso}"

  val buildPlugins = arrayListOf<String>().apply {
    add(androidGradle)
    add(kotlinGradlePlugin)
    add(navigationGradlePlugin)
//    add(hiltGradlePlugin)
  }

  val androidLibraries = arrayListOf<String>().apply {
    add(kotlinStdLib)
    add(coreKtx)
    add(viewModel)
    add(viewModelKtx)
    add(fragmentKtx)
    add(lifecycleKtx)
    add(appcompat)
    add(material)
    add(materialColor)
    add(multiDex)
    add(alerter)
    add(swipeRefreshVersion)
    add(constraintLayout)
    add(coroutineCore)
    add(coroutineAndroid)
    add(liveDataKtx)
    add(chucker)
    add(sqlchipper)
    add(sDp)
    add(sSp)
    add(shimmer)
    add(glide)
    add(timelineView)
    add(toasty)
    add(chartView)
    add(materialDialog)
    add(lottie)
    add(pageIndicatorView)
    add(photoView)
    add(pagingRuntime)
  }

  val kaptLibraries = arrayListOf<String>().apply {
    add(roomCompiler)
    add(moshiCodegen)
  }

  val dependencyInjectionLibraries = arrayListOf<String>().apply {
    add(koinCore)
    add(koinAndroid)
  }

  val networkLibraries = arrayListOf<String>().apply {
    add(retrofit)
    add(moshi)
    add(gsonRetrofitFactory)
    add(moshiRetrofitFactory)
    add(okHttpInterceptor)
  }

  val datastoreLibraries = arrayListOf<String>().apply {
    add(jetpackDatastore)
    add(jetpackDatastoreCore)
  }

  val persistenceLibraries = arrayListOf<String>().apply {
    add(roomRuntime)
    add(roomKtx)
  }

  val navigationLibraries = arrayListOf<String>().apply {
    add(navigationFragment)
    add(navigationUiKtx)
    add(navigationRuntime)
    add(navigationDynamicFeatures)
  }

  val androidTestLibraries = arrayListOf<String>().apply {
    add(extJUnit)
    add(espressoCore)
    add(archCoreTesting)
    add(espressoContrib)
    add(espressoIntent)
    add(truth)
    add(koinTest)
  }

  val testLibraries = arrayListOf<String>().apply {
    add(junit)
    add(coroutineTest)
    add(turbine)
    add(mockk)
    add(truth)
    add(archCoreTesting)
  }

  val firebasePlatformLibraries = arrayListOf<String>().apply {
    add(firebaseBom)
  }

  val firebaseLibraries = arrayListOf<String>().apply {
    add(firebaseAnalytics)
    add(firebaseCrashlytics)
  }
}

//util functions for adding the different type dependencies from build.gradle.kts file
fun DependencyHandler.classpathPlugin(list: List<String>) {
  list.forEach { dependency ->
    add("classpath", dependency)
  }
}

fun DependencyHandler.kapt(list: List<String>) {
  list.forEach { dependency ->
    add("kapt", dependency)
  }
}

fun DependencyHandler.implementation(list: List<String>) {
  list.forEach { dependency ->
    add("implementation", dependency)
  }
}

fun DependencyHandler.implementationPlatform(list: List<String>) {
  list.forEach { dependency ->
    add("implementation", platform(dependency))
  }
}

fun DependencyHandler.implementationProject(list: List<String>) {
  list.forEach { dependency ->
    add("implementation", project(dependency))
  }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
  list.forEach { dependency ->
    add("androidTestImplementation", dependency)
  }
}

fun DependencyHandler.testImplementation(list: List<String>) {
  list.forEach { dependency ->
    add("testImplementation", dependency)
  }
}