package id.co.app.nuco.main.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.extension.withDefault
import id.co.app.nucocore.singleton.HeaderSingleton
import id.co.app.nucocore.singleton.LOG_TAG
import id.co.app.session.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class MainState(val id: Int) {
  NONE(0),
  SHOW_BLOCKER_CONNECTION(1),
  SHOW_BLOCKER_DATA_SYNC(2),
  SHOW_BLOCKER_DATA_SYNC_SPK(3),

  REDIRECT_LOGIN(11),
  REDIRECT_HOME(12),
}

class MainViewModel(
  private val userSession: UserSession,
  private val dsRepo: DataSyncRepository
) : ViewModel() {

  private val _state = MutableLiveData(MainState.NONE)
  val mainStateLiveData: LiveData<MainState> get() = _state

  fun checkDataSyncState() {
    viewModelScope.launch(Dispatchers.IO) {
      val user = userSession.getUser().first()
      val userId = user?.user?.userId.withDefault("0")

      val dataSyncStatus = dsRepo.getOverviewState(userId).syncRequired()
      if (dataSyncStatus) {
        _state.postValue(MainState.SHOW_BLOCKER_DATA_SYNC)
      }

      val dataSyncSpkStatus = dsRepo.getSPKSyncState(userId).first().syncRequired()
      if (dataSyncSpkStatus) {
        _state.postValue(MainState.SHOW_BLOCKER_DATA_SYNC_SPK)
      }
    }
  }

  fun splashAccessCheckup(source: String) {
    _state.value = MainState.NONE
    // TODO LIST:
    // 01. Connection Validation
    // 02. Version Validation
    // 03. Application Integrity Validation
    // 04. Session Validation

    // doConnectionValidation()
    doSessionValidation(source)
  }

  // --
  @Suppress("unused")
  private fun doConnectionValidation() {
    viewModelScope.launch {
      delay(1000)

      _state.value = MainState.SHOW_BLOCKER_CONNECTION
    }
  }

  private fun doSessionValidation(source: String) {
    viewModelScope.launch {
      val user = userSession.getUser().first()
      if (user == null) {
        Log.d(LOG_TAG, "doSessionValidation($source) -> LOGIN: $user")
        _state.value = MainState.REDIRECT_LOGIN
      } else {
        HeaderSingleton.getDefaultInstance().updateToken("Bearer ${user.token}")
        Log.d(LOG_TAG, "doSessionValidation($source) -> HOME: $user")
        _state.value = MainState.REDIRECT_HOME
      }
    }
  }
}