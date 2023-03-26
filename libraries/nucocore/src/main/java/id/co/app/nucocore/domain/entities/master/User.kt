package id.co.app.nucocore.domain.entities.master

import java.io.Serializable

data class User(
  val username: String,
  val region: String,
  val token: String,
) : Serializable