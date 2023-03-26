package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SelectionDate(
  val mId: Int,
  val startDate: Long,
  val endDate: Long,
  val selectedDate: Long?,
) : Serializable, DelegateAdapterItem, BaseModel() {
  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is SelectionDate && other.mId == mId &&
      other.startDate == startDate && other.endDate == endDate &&
      other.selectedDate == selectedDate
  }

  override fun content(): Any {
    return "SelectionDate:$mId,$startDate,$endDate,$selectedDate"
  }
}