package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeEvoAModel(
  val name: String,
  val pokeId: Int,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = name

  override fun id(): Any = name

  override fun equals(other: BaseModel): Boolean {
    return other is PokeEvoAModel && other.name == name && other.pokeId == pokeId
  }

  override fun content(): Any {
    return "pokemon_evo_a:$name.$pokeId"
  }
}