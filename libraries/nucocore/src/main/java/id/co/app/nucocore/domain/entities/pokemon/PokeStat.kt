package id.co.app.nucocore.domain.entities.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokeStat(
  @SerializedName("base_stat")
  val baseStat: Int,
  val effort: Int,
  val stat: PokeResult,
): Serializable