package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import id.co.app.nucocore.domain.entities.pokemon.PokeStat
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.extension.pokemon.formatName
import java.io.Serializable

data class PokeInfo3Model(
  var pokeId: Int,
  val evoList: List<DelegateAdapterItem>,
) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = pokeId

  override fun id(): Any = pokeId

  override fun equals(other: BaseModel): Boolean {
    return other is PokeInfo3Model && other.pokeId == pokeId
  }

  override fun content(): Any {
    return "pokemon_info_3:$pokeId.${evoList.size}"
  }
}