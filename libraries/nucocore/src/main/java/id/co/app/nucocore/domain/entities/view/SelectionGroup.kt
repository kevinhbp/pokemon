package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SelectionGroup(
  val mId: Int,
  val type: Int,
  val textStart: String,
  val textEnd: String,
  var selected: Boolean = false,
  var value: Any,
) : Serializable, DelegateAdapterItem, BaseModel() {
  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectionGroup && other.mId == mId
  }

  override fun content(): Any {
    return "SelectionGeneral:$mId,$type,$selected,$textStart,$textEnd"
  }
}