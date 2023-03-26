package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SelectorButtonModel(
  val mId: Int,
  val label: String,
  val buttonLabel: String,
  val buttonEnabled: Boolean = true,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectorButtonModel && other.mId == mId && other.label == label && other.buttonLabel == buttonLabel
      && other.buttonEnabled == buttonEnabled
  }

  override fun content(): Any {
    return "SelectorButton:$mId,$label,$buttonLabel,$buttonEnabled"
  }
}