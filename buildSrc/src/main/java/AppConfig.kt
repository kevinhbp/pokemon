import java.text.SimpleDateFormat
import java.util.*

//app level config constants
object AppConfig {
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 101000
    const val versionName = "1.0.0"
    const val buildToolsVersion = "30.0.3"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules =  "consumer-rules.pro"
    const val dimension = "environment"

    const val appId = "id.co.app.nuco"
    const val appName = "Pokemon"

    // --
    const val BASE_URL_PRO = "https://pokeapi.co/api/v2/"
    const val BASE_URL_STG = "https://pokeapi.co/api/v2/"

    fun getBuildTime(): String {
        return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    }

    fun getApkFileName(): String {
        return "pokemon_app_v.$versionName.$versionCode.${getBuildTime()}"
    }
}