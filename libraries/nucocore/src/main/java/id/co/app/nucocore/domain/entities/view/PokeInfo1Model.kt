package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import java.io.Serializable

data class PokeInfo1Model(
  var pokeId: Int,
  val pokeName: String,
  val pokeWeight: String,
  val pokeHeight: String,
  val types: List<String>,
  val abilities: List<String>,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = pokeName

  override fun id(): Any = pokeName

  override fun equals(other: BaseModel): Boolean {
    return other is PokeInfo1Model && other.pokeName == pokeName
  }

  override fun content(): Any {
    var typeString = ""
    types.forEach { typeString += it }
    abilities.forEach { typeString += it }
    return "pokemon_type:$pokeId.$pokeName.$pokeWeight.$pokeHeight.$typeString"
  }

  companion object {
    fun fromPokemon(pokemon: Pokemon): PokeInfo1Model {
      val weight = (pokemon.weight/100).toInt().toString() + " Kg"
      val height = (pokemon.height/10).toInt().toString() + " M"
      val type = pokemon.types.map { e -> e.type.name }
      val abilities = pokemon.abilities.map { e -> e.ability.name }
      return PokeInfo1Model(pokemon.id, pokemon.name, weight, height, type, abilities)
    }
  }
}