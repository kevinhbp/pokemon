package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokemonListOnType(
  val id: Int,
  val name: String,
  val pokemon: List<PokemonOnType>
): Serializable