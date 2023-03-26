package id.co.app.nucocore.domain.entities.master

import java.io.Serializable

data class Header(
  var token: String,
  val appName: String,
  val appVersionName: String,
  val appVersionCode: String,
  val deviceId: String,
) : Serializable