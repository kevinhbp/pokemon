package id.co.app.nucocore.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import id.co.app.nucocore.BuildConfig
import id.co.app.nucocore.domain.interceptor.HttpRequestInterceptor
import id.co.app.nucocore.domain.mock.main.MainMockStorage
import id.co.app.nucocore.domain.network.nuco.MainService
import id.co.app.nucocore.domain.network.nuco.MainClient
import id.co.app.nucocore.domain.persistence.*
import id.co.app.nucocore.domain.repository.*
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object InjectionModule {
  val networkModule = module {
    single { getOkHttpClient(androidContext()) }
    single { getMainMockStorage(androidContext()) }

    single(named("production")) { getRetrofit(get(), BuildConfig.URL_PRO) }
    single(named("production")) { getMainService(get(named("production"))) }

    single(named("staging")) { getRetrofit(get(), BuildConfig.URL_STG) }
    single(named("staging")) { getMainService(get(named("staging"))) }

    single { getMainClient(get(named("production")), get(named("staging"))) }
  }

  // --
  val persistenceModule = module {
    fun getMoshi(): Moshi {
      return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    fun getTypeConverter(moshi: Moshi): TypeConverter {
      return TypeConverter(moshi)
    }

    fun getAppDatabase(context: Context, typeConverter: TypeConverter): AppDatabase {
      return Room
        .databaseBuilder(context, AppDatabase::class.java, "App.db")
        .fallbackToDestructiveMigration()
        .addTypeConverter(typeConverter)
        .build()
    }

    fun getPokemonDao(db: AppDatabase): PokemonDao {
      return db.pokemonDao()
    }

    single { getMoshi() }
    single { getTypeConverter(get()) }
    single { getAppDatabase(get(), get()) }

    single { getPokemonDao(get()) }
  }

  // --
  val repositoryModule = module {

    fun getMainRepository(client: MainClient, pokemonDao: PokemonDao, mockStorage: MainMockStorage): MainRepository {
      return MainRepository(client, pokemonDao, mockStorage)
    }

    single { getMainRepository(get(), get(), get()) }
  }

  // --
  // Create functions that return required instance first.
  private fun getOkHttpClient(context: Context): OkHttpClient {
    val logInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
    val builder = OkHttpClient.Builder()
      .addInterceptor(logInterceptor)
      .addInterceptor(HttpRequestInterceptor())
      .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
      .protocols(listOf(Protocol.HTTP_1_1))
      .retryOnConnectionFailure(true)
      .connectTimeout(5, TimeUnit.SECONDS)
      .readTimeout(5, TimeUnit.SECONDS)
      .writeTimeout(5, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) builder.addInterceptor(
      ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()
    )
    return builder.build()
  }

  private fun getRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  private fun getMainService(retrofit: Retrofit): MainService {
    return retrofit.create(MainService::class.java)
  }

  private fun getMainClient(production: MainService, staging: MainService): MainClient {
    return MainClient(production, staging)
  }

  private fun getMainMockStorage(context: Context): MainMockStorage = MainMockStorage(context)
}