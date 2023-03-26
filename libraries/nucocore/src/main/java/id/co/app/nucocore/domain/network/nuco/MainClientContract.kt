package id.co.app.nucocore.domain.network.nuco

import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.entities.pokemon.Pokemon

interface MainClientContract {

  interface MainCommon {
    suspend fun getPokemonList(offset: Int, limit: Int): List<PokeResult>
    suspend fun getPokemonDetail(id: Any): Pokemon
  }
}