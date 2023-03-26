package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.R
import java.io.Serializable

data class SubtitleTextWithButtonModel(val mid: Int, val text: String, val buttonLabel: String, val buttonTarget: String)
  : Serializable, DelegateAdapterItem, BaseModel() {
  var colorId: Int = R.color.color_text_black_1
  var heightInPx: Int? = null

  override fun id(): Any {
    return mid.toString() + text
  }

  override val id: Any
    get() = mid.toString() + text

  override fun equals(other: BaseModel): Boolean {
    return other is SubtitleTextWithButtonModel && other.text == text && other.buttonLabel == buttonLabel
  }

  override fun content(): Any {
    return text + buttonLabel
  }
}