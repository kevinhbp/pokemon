package id.co.app.nucocore.domain.entities.pokemon

import java.io.Serializable

data class PokeType(
  val slot: Int,
  val type: PokeResult,
): Serializable