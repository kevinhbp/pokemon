package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SelectorSwitchModel(
  val mId: Int,
  val label: String,
  val buttonLabelA: String,
  val buttonLabelB: String,
  val switchValue: Boolean,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectorSwitchModel && other.mId == mId
  }

  override fun content(): Any {
    return "SelectorButton:$mId,$label,$buttonLabelA,$buttonLabelB,$switchValue"
  }
}