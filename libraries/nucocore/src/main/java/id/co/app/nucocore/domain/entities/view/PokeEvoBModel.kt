package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeEvoBModel(
  val description: String,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = description

  override fun id(): Any = description

  override fun equals(other: BaseModel): Boolean {
    return other is PokeEvoBModel && other.description == description
  }

  override fun content(): Any {
    return "pokemon_evo_b:$description"
  }
}