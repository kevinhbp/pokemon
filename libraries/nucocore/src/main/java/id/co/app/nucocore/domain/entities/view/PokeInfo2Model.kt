package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.PokeStat
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.extension.pokemon.formatName
import java.io.Serializable

data class PokeInfo2Model(
  var pokeId: Int,
  val otherImages: List<String>,
  val stats: List<PokeStat>,
  val evolution: List<String>,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = pokeId

  override fun id(): Any = pokeId

  override fun equals(other: BaseModel): Boolean {
    return other is PokeInfo2Model && other.pokeId == pokeId
  }

  override fun content(): Any {
    var contentString = ""
    otherImages.forEach { contentString += ("$it.") }
    stats.forEach { contentString += "${it.stat.name}." }
    evolution.forEach { contentString += "$it." }
    return "pokemon_info_2:$pokeId.$contentString"
  }

  companion object {
    fun fromPokemon(pokemon: Pokemon): PokeInfo2Model {
      val id = pokemon.id

      val otherImages = arrayListOf<String>()
      val sprites = pokemon.sprites
      if (sprites.frontDefault.isNotEmpty()) {
        otherImages.add(sprites.frontDefault)
      }
      if (sprites.backDefault.isNotEmpty()) {
        otherImages.add(sprites.backDefault)
      }
      if (sprites.frontShiny.isNotEmpty()) {
        otherImages.add(sprites.frontShiny)
      }
      if (sprites.backShiny.isNotEmpty()) {
        otherImages.add(sprites.backShiny)
      }
      if (!sprites.frontFemale.isNullOrEmpty()) {
        otherImages.add(sprites.frontFemale)
      }
      if (!sprites.backFemale.isNullOrEmpty()) {
        otherImages.add(sprites.backFemale)
      }

      val stats = listOf<PokeStat>()
      val evolution = listOf<String>()
      return PokeInfo2Model(id, otherImages, stats, evolution)
    }
  }
}