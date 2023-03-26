package id.co.app.nucocore.domain.network.nuco

import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.network.EnvironmentCode
import id.co.app.nucocore.singleton.DataSingleton

class MainClient(private val production: MainService, private val staging: MainService) :
  MainClientContract.MainCommon {

  private fun getService(): MainService {
    return when (DataSingleton.getDefaultInstance().currentEnvironmentCode) {
      EnvironmentCode.DEVELOPMENT -> staging
      else -> production
    }
  }

  override suspend fun getPokemonList(offset: Int, limit: Int): List<PokeResult> {
    return getService().getPokemonList(offset, limit).results
  }

  override suspend fun getPokemonDetail(id: Any): Pokemon {
    return getService().getPokemonDetail(id)
  }
}