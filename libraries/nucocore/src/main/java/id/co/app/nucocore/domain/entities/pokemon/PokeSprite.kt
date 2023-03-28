package id.co.app.nucocore.domain.entities.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokeSprite(
  @SerializedName("front_default")
  val frontDefault: String,
  @SerializedName("front_shiny")
  val frontShiny: String,
  @SerializedName("back_default")
  val backDefault: String,
  @SerializedName("back_shiny")
  val backShiny: String,
  @SerializedName("front_female")
  val frontFemale: String?,
  @SerializedName("front_shiny_female")
  val frontShinyFemale: String?,
  @SerializedName("back_female")
  val backFemale: String?,
  @SerializedName("back_shiny_female")
  val backShinyFemale: String?,
): Serializable