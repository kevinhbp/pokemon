package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeCardModel(
  val pokeId: Int,
  val pokeName: String,
  val types: List<String>,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = pokeId

  override fun id(): Any = pokeId

  override fun equals(other: BaseModel): Boolean {
    return other is PokeCardModel && other.pokeId == pokeId &&
      other.pokeName == pokeName && other.types == types
  }

  override fun content(): Any {
    return "pokemon_type:$pokeId.$pokeName.$types"
  }
}