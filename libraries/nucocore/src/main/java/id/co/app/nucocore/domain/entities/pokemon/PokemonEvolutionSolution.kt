package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokemonEvolutionSolution(
  val from: Pokemon,
  val to: Pokemon,
): Serializable