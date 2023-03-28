package id.co.app.nucocore.singleton

import id.co.app.nucocore.domain.entities.pokemon.Pokemon
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

  var selectedPokemon: Pokemon? = null

  var currentEnvironmentCode: EnvironmentCode = EnvironmentCode.PRODUCTION
}