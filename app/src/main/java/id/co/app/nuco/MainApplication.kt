package id.co.app.nuco

import android.util.Log
import androidx.multidex.MultiDexApplication
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nuco.main.viewModels.MainViewModel
import id.co.app.nuco.splash.viewModels.SplashViewModel
import id.co.app.nucocore.components.blocker.BlockerViewModel
import id.co.app.nucocore.di.InjectionModule
import id.co.app.nucocore.domain.entities.master.Header
import id.co.app.nucocore.singleton.HeaderSingleton
import id.co.app.nucocore.singleton.LOG_TAG
import id.co.app.detail.viewModels.DetailViewModel
import id.co.app.session.UserSessionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MainApplication : MultiDexApplication() {

  override fun onCreate() {
    super.onCreate()
    HeaderSingleton.getDefaultInstance().header = Header(
      "",
      getString(R.string.app_name),
      BuildConfig.VERSION_NAME,
      BuildConfig.VERSION_CODE.toString(),
      ""
    )
    startKoinModule()
  }

  // --
  private fun startKoinModule() {
    Log.d(LOG_TAG, "[KOIN] - Start Module")
    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@MainApplication)

      // Modules
      modules(
        listOf(
          InjectionModule.persistenceModule,
          UserSessionModule.userSessionModule,
          InjectionModule.networkModule,
          InjectionModule.repositoryModule,
          useCaseModule,
          viewModelModule
        )
      )
    }
  }

  private val useCaseModule = module {

  }

  private val viewModelModule = module {
    Log.d(LOG_TAG, "[KOIN] - Start Module: View Model")
    viewModel { MainViewModel() }

    viewModel { BlockerViewModel() }

    viewModel { SplashViewModel() }

    viewModel { HomeViewModel(get()) }

    viewModel { DetailViewModel(get()) }
  }
}