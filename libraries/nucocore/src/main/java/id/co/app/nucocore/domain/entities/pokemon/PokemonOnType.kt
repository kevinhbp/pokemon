package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokemonOnType(
  val pokemon: PokeResult,
  val slot: Int,
): Serializable