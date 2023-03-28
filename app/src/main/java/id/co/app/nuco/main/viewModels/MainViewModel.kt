package id.co.app.nuco.main.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.app.nucocore.domain.entities.view.ActionBarMenuModel

enum class MainState(val id: Int) {
  NONE(0),
  SHOW_BLOCKER_CONNECTION(1),
  REDIRECT_HOME(12),
}

class MainViewModel : ViewModel() {

  private val _state = MutableLiveData(MainState.NONE)
  val mainStateLiveData: LiveData<MainState> get() = _state

  private val _menu = MutableLiveData(ActionBarMenuModel(home = true, types = false))
  val menu: LiveData<ActionBarMenuModel> get() = _menu

  fun setMenuHome() {
    val newMenu = ActionBarMenuModel(home = true, types = false)
    _menu.postValue(newMenu)
  }

  fun setMenuTypes() {
    val newMenu = ActionBarMenuModel(home = false, types = true)
    _menu.postValue(newMenu)
  }
}