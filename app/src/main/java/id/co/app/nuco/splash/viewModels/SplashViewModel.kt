package id.co.app.nuco.splash.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.app.nuco.BuildConfig
import id.co.app.nuco.splash.model.SplashModel

class SplashViewModel : ViewModel() {
  private val _splashModel = MutableLiveData(SplashModel(""))
  val splashModelLiveData: LiveData<SplashModel> = _splashModel

  fun start() {
    val versionText = "Version ${BuildConfig.VERSION_NAME}"
    _splashModel.value = SplashModel(versionText)
  }
}