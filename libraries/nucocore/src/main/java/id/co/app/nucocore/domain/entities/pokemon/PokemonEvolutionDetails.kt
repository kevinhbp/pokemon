package id.co.app.nucocore.domain.entities.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonEvolutionDetails(
  @SerializedName("min_level")
  val minLevel: Int?,
): Serializable