package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SelectionButtonModel(
  val mId: Int, val name: String, val backgroundImage: Int, val selected: Boolean,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectionButtonModel &&
      other.mId == mId && other.name == name &&
      other.backgroundImage == backgroundImage &&
      other.selected == selected
  }

  override fun content(): Any {
    return "SelectionButton:$mId,$name,$backgroundImage,$selected"
  }
}