repositories {
  google()
  mavenCentral()
  maven { setUrl("https://jitpack.io") }
}

plugins {
  `kotlin-dsl`
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}