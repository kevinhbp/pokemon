package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeTypeButtonModel(
  val mId: Int,
  val types: List<String>,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = mId

  override fun id(): Any = mId

  override fun equals(other: BaseModel): Boolean {
    return other is PokeTypeButtonModel && other.mId == mId
  }

  override fun content(): Any {
    var typesText = ""
    types.forEach { e -> typesText += e }
    return "PokeTypeButtonModel:$mId.$typesText"
  }
}