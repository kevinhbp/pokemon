package id.co.app.nucocore.domain.network.nuco

import id.co.app.nucocore.domain.entities.pokemon.*
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

  override suspend fun getPokemonList(offset: Int, limit: Int): PokemonList {
    return getService().getPokemonList(offset, limit)
  }

  override suspend fun getPokemonDetail(id: Any): Pokemon {
    return getService().getPokemonDetail(id)
  }

  override suspend fun getPokemonTypeList(): PokemonList {
    return getService().getPokemonTypeList()
  }

  override suspend fun getPokemonEvolutionChain(id: Any): PokemonEvolution {
    return getService().getPokemonEvolutionChain(id)
  }

  override suspend fun getPokemonWithType(id: Any): PokemonListOnType {
    return getService().getPokemonWithType(id)
  }
}