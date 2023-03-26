package id.co.app.nucocore.singleton

import id.co.app.nucocore.domain.network.EnvironmentCode

class DataSingleton {
  companion object {
    private lateinit var instance: DataSingleton

    fun getDefaultInstance(): DataSingleton {
      if (!::instance.isInitialized) {
        instance = DataSingleton()
      }

      return instance
    }
  }

  val useMockStorage: Boolean = false

  var currentEnvironmentCode: EnvironmentCode = EnvironmentCode.PRODUCTION
}