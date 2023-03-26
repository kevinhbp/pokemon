package id.co.app.nucocore.domain.entities.pokemon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokeAbility(
  val ability: PokeResult,
  @SerializedName("is_hidden") val isHidden: Boolean,
  val slot: Int
): Serializable