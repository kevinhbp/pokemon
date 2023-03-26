package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.R
import java.io.Serializable

data class DefaultPairTextModel(val label: String, val value: String)
  : Serializable, DelegateAdapterItem, BaseModel() {
  var labelColorId: Int = R.color.color_text_black_1
  var valueColorId: Int = R.color.color_text_gray_0
  var heightInPx: Int? = null

  override fun id(): Any {
    return label
  }

  override val id: Any
    get() = label

  override fun equals(other: BaseModel): Boolean {
    return other is DefaultPairTextModel && other.label == label && other.value == value
  }

  override fun content(): Any {
    return label
  }
}