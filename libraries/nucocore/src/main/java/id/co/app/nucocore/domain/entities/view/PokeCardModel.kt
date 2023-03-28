package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import java.io.Serializable

data class PokeCardModel(
  var pokeId: Int,
  val pokeName: String,
  val types: List<String>,
  val pokemon: Pokemon,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = pokeName

  override fun id(): Any = pokeName

  override fun equals(other: BaseModel): Boolean {
    return other is PokeCardModel && other.pokeName == pokeName &&
      getTypeString(other.types) == getTypeString(types)
  }

  override fun content(): Any {
    return "pokemon_type:$pokeId.$pokeName.${getTypeString(types)}"
  }

  private fun getTypeString(types: List<String>) : String {
    var result = ""
    types.forEach { result += "$it." }
    return result
  }

  companion object {

    fun fromPokemon(pokemon: Pokemon): PokeCardModel {
      return PokeCardModel(
        pokemon.id,
        pokemon.name,
        pokemon.types.map { e -> e.type.name },
        pokemon = pokemon
      )
    }
  }
}