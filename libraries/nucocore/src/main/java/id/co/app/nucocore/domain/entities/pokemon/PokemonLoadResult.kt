package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokemonLoadResult(
  val count: Int,
  val results: List<Pokemon>,
): Serializable