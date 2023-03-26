package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.extension.toLongDate
import java.io.Serializable

data class SelectorListButtonModel(
  val mId: Int,
  val label: String,
  val buttons: List<SelectionButtonModel>,
  val updateTime: Long,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectorListButtonModel && other.mId == mId && other.label == label && other.buttons == buttons
      && other.updateTime == updateTime
  }

  override fun content(): Any {
    return "SelectorButton:$mId,$label,$updateTime"
  }

  fun getTextUpdateTime(): String = updateTime.toLongDate()
}