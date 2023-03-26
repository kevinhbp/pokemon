package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokemonList(
  val count: Int,
  val results: List<PokeResult>,
  val next: String?,
  val previous: String?,
): Serializable