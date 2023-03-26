package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SelectionGeneral(
  val mId: Int,
  val type: Int,
  val text: String,
  val value: Any,
  var selected: Boolean = false,
) : Serializable, DelegateAdapterItem, BaseModel() {
  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectionGeneral && other.mId == mId &&
      other.text == text && other.selected == selected && other.type == type
  }

  override fun content(): Any {
    return "SelectionGeneral:$mId,$text,$selected,$type"
  }
}