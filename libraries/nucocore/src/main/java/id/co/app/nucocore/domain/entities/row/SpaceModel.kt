package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class SpaceModel(val heightInPx: Int) : Serializable, DelegateAdapterItem, BaseModel() {
  override val id: Any
    get() = heightInPx

  override fun equals(other: BaseModel): Boolean {
    return other is SpaceModel && other.heightInPx == heightInPx
  }

  override fun content(): Any {
    return "Space($heightInPx)"
  }

  override fun id(): Any {
    return heightInPx
  }
}