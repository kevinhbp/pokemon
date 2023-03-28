package id.co.app.nucocore.domain.entities.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonEvolutionChain(
  @SerializedName("evolution_details")
  val evolutionDetails: List<PokemonEvolutionDetails>,
  @SerializedName("evolves_to")
  val evolvesTo: List<PokemonEvolutionChain>,
  val species: PokeResult
): Serializable