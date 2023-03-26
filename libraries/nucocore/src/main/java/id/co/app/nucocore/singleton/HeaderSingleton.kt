package id.co.app.nucocore.singleton

import id.co.app.nucocore.BuildConfig
import id.co.app.nucocore.domain.entities.master.Header

class HeaderSingleton {

  var header: Header? = null

  fun updateToken(token: String) {
    if (header == null) {
      header = Header(token, "Pokemon", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, "")
      return
    }

    header?.token = token
  }

  companion object {
    private lateinit var instance: HeaderSingleton

    fun getDefaultInstance(): HeaderSingleton {
      if (!::instance.isInitialized) {
        instance = HeaderSingleton()
      }

      return instance
    }
  }
}