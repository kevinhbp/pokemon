package id.co.app.home.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.home.model.HomeWidgetsModel
import id.co.app.nucocore.R
import id.co.app.nucocore.deeplink.InternalDeepLink
import id.co.app.nucocore.domain.entities.assessment.DailyProgressAssessment
import id.co.app.nucocore.domain.entities.assessment.StockAssessment
import id.co.app.nucocore.domain.entities.login.User
import id.co.app.nucocore.domain.entities.master.DataSync
import id.co.app.nucocore.domain.entities.row.*
import id.co.app.nucocore.extension.toLongDate
import id.co.app.nucocore.extension.toPx
import id.co.app.nucocore.extension.withDefault
import id.co.app.session.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
  private val usse: UserSession,
  private val dataSyncRepo: DataSyncRepository,
) : ViewModel() {

  private val _contentData = MutableLiveData<MutableList<DelegateAdapterItem>>()
  val contentData: LiveData<MutableList<DelegateAdapterItem>> get() = _contentData

  private val _loading = MutableLiveData(false)
  val loading: LiveData<Boolean> get() = _loading

  // --
  private var user: User? = null
  private var syncState: DataSync? = null
  private val dpActivityList = ArrayList<DailyProgressAssessment>()
  private val soActivityList = ArrayList<StockAssessment>()

  fun loadContent() {
    viewModelScope.launch(Dispatchers.IO) {
      // Show Loading
      _loading.postValue(true)

      // User
      user = usse.getUser().firstOrNull()?.user
      val userId = user?.userId.withDefault("0")
      // Sync Data
      syncState = dataSyncRepo.getOverviewState(userId)
      // User Activity
      dpActivityList.clear()
      dpActivityList.addAll(dataSyncRepo.getDPActivity(userId).first())
      soActivityList.clear()
      soActivityList.addAll(dataSyncRepo.getSOActivity(userId).first())

      refreshContent()

      // Hide Loading
      delay(400)
      _loading.postValue(false)
    }
  }

  // --
  private fun refreshContent() {
    val contentList = ArrayList<DelegateAdapterItem>()
    contentList.apply {
      add(SpaceModel(120.toPx()))
      add(getWelcomeMessageRow())
      add(getMainHomeWidgetsModel())
      add(SpaceModel(8.toPx()))
      add(getLastActivitiesSubtitle())
      add(SpaceModel(8.toPx()))
      val lastActivityList = getLastActivityList()
      if (lastActivityList.isEmpty()) {
        add(getEmptyStateRow())
      } else {
        addAll(lastActivityList)
      }
      add(SpaceModel(90.toPx()))
    }
    _contentData.postValue(contentList)
  }

  private fun getWelcomeMessageRow(): HeroTextModel {
    val username = user?.fullname ?: "User"
    val c = Calendar.getInstance()
    val welcome: String = when (c[Calendar.HOUR_OF_DAY]) {
      in 0..11 -> "Good Morning"
      in 12..15 -> "Good Afternoon"
      in 16..20 -> "Good Evening"
      in 21..24 -> "Good Night"
      else -> ""
    }
    return HeroTextModel("$welcome,\nWelcome Back $username.").apply {
      colorId = R.color.color_text_white_0
      heightInPx = 100.toPx()
    }
  }

  private fun getMainHomeWidgetsModel(): HomeWidgetsModel {
    val dataSyncRequired = syncState?.syncRequired() ?: false
    val hasUnreadNotification = false
    val lastSync = syncState?.lastSync?.toLongDate() ?: "Last sync is not found.."
    val unreadNotificationCount = 0

    val readTime = System.currentTimeMillis()
    return HomeWidgetsModel(1, dataSyncRequired, hasUnreadNotification, readTime).apply {
      dataSyncMessage = "Last sync: $lastSync"
      dataSyncClickTarget = InternalDeepLink.DATA_SYNC.format("no")
      val mCount = if (unreadNotificationCount < 1) "no" else unreadNotificationCount.toString()
      notificationsMessage = "You have $mCount unread\nnotifications."
    }
  }

  private fun getLastActivitiesSubtitle(): SubtitleTextModel {
    return SubtitleTextModel("Last Activities").apply {
      colorId = R.color.color_text_black_1
    }
  }

  private fun getLastActivityList(): List<ActivityModel> {
    val result: ArrayList<ActivityModel> = arrayListOf()
    soActivityList.forEachIndexed { index, d ->
      result.add(ActivityModel.fromStockAssessment(index, d))
    }
    dpActivityList.forEachIndexed { index, d ->
      result.add(ActivityModel.fromDailyProgressAssessment(index, d, true))
    }
    result.sortDescending()
    return result
  }

  private fun getEmptyStateRow(): DefaultEmptyStateModel {
    return DefaultEmptyStateModel(
      "noData", R.drawable.illustration_no_activities,
      "No Activities", "User didn't have any activities yet.",
    )
  }
}