package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokemonEvolution(
  val chain: PokemonEvolutionChain,
  val id: Int
): Serializable