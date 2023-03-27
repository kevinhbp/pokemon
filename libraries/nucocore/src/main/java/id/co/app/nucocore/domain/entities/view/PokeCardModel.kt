package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeCardModel(
  var pokeId: Int,
  val pokeName: String,
  val types: List<String>,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = pokeName

  override fun id(): Any = pokeName

  override fun equals(other: BaseModel): Boolean {
    return other is PokeCardModel && other.pokeName == pokeName
  }

  override fun content(): Any {
    var typeString = ""
    types.forEach { typeString += it }
    return "pokemon_type:$pokeId.$pokeName.$typeString"
  }
}