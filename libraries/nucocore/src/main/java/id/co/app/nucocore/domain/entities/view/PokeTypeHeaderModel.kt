package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeTypeHeaderModel(
  val typeName: String,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = typeName

  override fun id(): Any = typeName

  override fun equals(other: BaseModel): Boolean {
    return other is PokeTypeHeaderModel && other.typeName == typeName
  }

  override fun content(): Any {
    return "pokemon_type_header:$typeName"
  }
}