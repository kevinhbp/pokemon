package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class ButtonPrimaryDestructiveModel(
  val mId: Int,
  val label: String,
  val enable: Boolean,
  val loading: Boolean,
  val target: String,
  val refreshTime: Long,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = "button$mId"

  override fun equals(other: BaseModel): Boolean =
    (other is ButtonPrimaryDestructiveModel &&
      other.refreshTime == refreshTime &&
      other.mId == mId &&
      other.label == label &&
      other.enable == enable &&
      other.loading == loading &&
      other.target == target
      )

  override fun content(): Any {
    return "ButtonPrimaryDestructive=$mId;$label; (refreshTime: $refreshTime) (loading: $loading) - (enabled: $enable)"
  }

  override fun id(): Any = "button$mId"

  // --
  fun isGoneLoading(): Boolean = (!loading)
}