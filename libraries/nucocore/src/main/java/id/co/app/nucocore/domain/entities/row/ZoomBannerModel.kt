package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class ZoomBannerModel(val mId: Long, val imageHash: String) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = "zoomBanner$mId"

  override fun equals(other: BaseModel): Boolean {
    return other is ZoomBannerModel && other.mId == mId && other.imageHash == imageHash
  }

  override fun content(): Any {
    return "Zoom Banner: $mId,$imageHash"
  }

  override fun id(): Any = "zoomBanner$mId"

  companion object {

  }
}