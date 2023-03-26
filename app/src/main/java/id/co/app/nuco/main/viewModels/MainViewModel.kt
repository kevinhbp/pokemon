package id.co.app.nuco.main.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class MainState(val id: Int) {
  NONE(0),
  SHOW_BLOCKER_CONNECTION(1),
  REDIRECT_HOME(12),
}

class MainViewModel : ViewModel() {

  private val _state = MutableLiveData(MainState.NONE)
  val mainStateLiveData: LiveData<MainState> get() = _state

  fun splashAccessCheckup(source: String) {
    _state.value = MainState.NONE
  }
}