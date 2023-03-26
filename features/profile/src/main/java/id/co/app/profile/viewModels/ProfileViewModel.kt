package id.co.app.profile.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.BuildConfig
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.domain.entities.row.ButtonPrimaryDestructiveModel
import id.co.app.nucocore.domain.entities.row.DefaultMenuItem
import id.co.app.nucocore.domain.entities.row.SpaceModel
import id.co.app.nucocore.extension.toPx
import id.co.app.nucocore.singleton.DataSingleton
import id.co.app.nucocore.singleton.LOG_TAG
import id.co.app.profile.model.HeaderProfileModel
import id.co.app.session.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel(
  private val userSession: UserSession,
) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentLiveData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private var mUsername = "guest"
  private var mLocation = "unknown"
  private var mPID = "-"

  fun loadData() {
    viewModelScope.launch {
      val res = userSession.getUser().firstOrNull()
      mUsername = res?.user?.fullname ?: ""
      mLocation = res?.user?.plantcode ?: ""
      mPID = res?.user?.pernr.toString()

      refreshContent(false)
    }
  }

  private fun refreshContent(loading: Boolean) {
    val refreshTime = System.currentTimeMillis()
    Log.d(LOG_TAG, "profileRefreshContent: $refreshTime - loading?$loading")
    val content = ArrayList<DelegateAdapterItem>()
    val version = "Ver " + BuildConfig.VERSION_NAME + "-" + BuildConfig.VERSION_CODE
    val environment = "Environment - " + DataSingleton.getDefaultInstance().currentEnvironmentCode.mName
    content.add(SpaceModel(12.toPx()))
    content.add(HeaderProfileModel(1, mUsername, mLocation, mPID))
    content.add(SpaceModel(4.toPx()))
    // content.add(DefaultMenuItem(2, "Privacy Policy", InternalDeepLink.PRIVACY_POLICY).withEndArrow())
    // content.add(DefaultMenuItem(3, "Terms and Conditions", InternalDeepLink.TERMS_AND_CONDITIONS).withEndArrow())
    content.add(DefaultMenuItem(4, version, ""))
    if (BuildConfig.DEBUG) {
      content.add(DefaultMenuItem(5, environment, ""))
    }
    content.add(SpaceModel(24.toPx()))
    content.add(ButtonPrimaryDestructiveModel(5, "Logout", enable = true, loading, InternalDeepLink.LOGOUT, refreshTime))
    _contentData.value = content.toMutableList()
  }

  fun doLogout(after: () -> Unit) {
    refreshContent(true)

    viewModelScope.launch {
      delay(500)
      userSession.deleteUser()
      after.invoke()
    }
  }
}