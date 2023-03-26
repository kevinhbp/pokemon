package id.co.app.nucocore.domain.entities.row

import android.widget.ImageView
import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class DefaultBannerModel(val mId: Long, val imageHash: String) : Serializable, DelegateAdapterItem, BaseModel() {

  var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP

  override val id: Any get() = "banner$mId"

  override fun equals(other: BaseModel): Boolean {
    return other is DefaultBannerModel && other.mId == mId && other.imageHash == imageHash
  }

  override fun content(): Any {
    return "Banner: $mId,$imageHash"
  }

  override fun id(): Any = "banner$mId"
}