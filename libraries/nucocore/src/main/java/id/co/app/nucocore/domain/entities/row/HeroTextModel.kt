package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.R
import java.io.Serializable

data class HeroTextModel(val text: String) : Serializable, DelegateAdapterItem, BaseModel() {
  var colorId: Int = R.color.color_text_black_1
  var heightInPx: Int? = null

  override fun equals(other: BaseModel): Boolean {
    return other is HeroTextModel && other.text == text
  }

  override fun id(): Any {
    return text
  }

  override val id: Any
    get() = text

  override fun content(): Any {
    return text
  }
}