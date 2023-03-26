package id.co.app.nuco

import android.util.Log
import androidx.multidex.MultiDexApplication
import id.co.app.dailyprogress.viewModels.DpViewModel
import id.co.app.home.viewModels.HomeViewModel
import id.co.app.nuco.login.viewModels.LoginViewModel
import id.co.app.nuco.main.viewModels.MainViewModel
import id.co.app.nuco.splash.viewModels.SplashViewModel
import id.co.app.nucocore.components.blocker.BlockerViewModel
import id.co.app.nucocore.components.bottomNavBar.BottomNavigationBarViewModel
import id.co.app.nucocore.di.InjectionModule
import id.co.app.nucocore.domain.entities.master.Header
import id.co.app.nucocore.singleton.HeaderSingleton
import id.co.app.nucocore.singleton.LOG_TAG
import id.co.app.nurseryassessment.viewModels.NaStartAssessmentViewModel
import id.co.app.nurseryassessment.viewModels.NaVEAssessmentViewModel
import id.co.app.nurseryassessment.viewModels.NaViewModel
import id.co.app.pnd.viewModels.PndDetailsViewModel
import id.co.app.pnd.viewModels.PndViewModel
import id.co.app.profile.viewModels.ProfileViewModel
import id.co.app.session.UserSessionModule
import id.co.app.setting.viewModels.SettingsViewModel
import id.co.app.stockassessment.viewModels.SoDetailsViewModel
import id.co.app.stockassessment.viewModels.SoViewModel
import id.co.app.synchro.domain.usecase.DoSyncDataUseCase
import id.co.app.synchro.domain.usecase.GetDataSyncUseCase
import id.co.app.synchro.viewModels.SynchroViewModel
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
    single { GetDataSyncUseCase(get(), get()) }
    single { DoSyncDataUseCase(get()) }
  }

  private val viewModelModule = module {
    Log.d(LOG_TAG, "[KOIN] - Start Module: View Model")
    viewModel { MainViewModel(get(), get()) }

    viewModel { BottomNavigationBarViewModel() }

    viewModel { BlockerViewModel() }

    viewModel { SplashViewModel() }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { HomeViewModel(get(), get()) }

    viewModel { PndViewModel(get()) }

    viewModel { NaViewModel() }

    viewModel { NaStartAssessmentViewModel() }

    viewModel { NaVEAssessmentViewModel(get()) }

    viewModel { DpViewModel(get(), get()) }

    viewModel { SoViewModel(get(), get()) }

    viewModel { SoDetailsViewModel(get(), get(), get(), get(), get()) }

    viewModel { ProfileViewModel(get()) }

    viewModel { PndDetailsViewModel() }

    viewModel { SynchroViewModel(get(), get(), get()) }

    viewModel { SettingsViewModel() }
  }
}