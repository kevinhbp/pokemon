package id.co.app.nucocore.domain.entities

import java.io.Serializable

open class BaseResponse : Serializable {
  val status: Boolean? = null
  val code: Int? = null
  val message: String? = null
}