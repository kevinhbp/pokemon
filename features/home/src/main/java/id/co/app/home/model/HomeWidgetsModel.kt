package id.co.app.home.model

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class HomeWidgetsModel(
  val widgetId: Int,
  var dataSyncRequired: Boolean = false,
  var notificationsUnread: Boolean = false,
  val readTime: Long
) : Serializable, DelegateAdapterItem, BaseModel() {
  var dataSyncMessage: String = ""
  var dataSyncClickTarget: String = ""

  var notificationsMessage: String = ""
  var notificationsClickTarget: String = ""

  override val id: Any
    get() = widgetId

  override fun equals(other: BaseModel): Boolean {
    return other is HomeWidgetsModel &&
      other.readTime == readTime &&
      other.widgetId == widgetId &&
      other.dataSyncRequired == dataSyncRequired &&
      other.notificationsUnread == notificationsUnread &&
      other.dataSyncMessage == dataSyncMessage &&
      other.notificationsMessage == notificationsMessage
  }

  override fun content(): Any {
    return "Home Main Widget: $widgetId ($readTime) - data: $dataSyncRequired - notification: $notificationsUnread"
  }

  override fun id(): Any {
    return widgetId
  }

  fun getDataSyncLabel(): String {
    return if (dataSyncRequired) {
      "Data Sync: Required"
    } else {
      "Data Sync"
    }
  }

  fun getNotificationsLabel(): String {
    return "Notifications"
  }
}