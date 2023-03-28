package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeStatModel(
  val name: String,
  val value: String,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = name

  override fun id(): Any = name

  override fun equals(other: BaseModel): Boolean {
    return other is PokeStatModel && other.name == name
  }

  override fun content(): Any {
    return "pokemon_stat:$name.$value"
  }
}