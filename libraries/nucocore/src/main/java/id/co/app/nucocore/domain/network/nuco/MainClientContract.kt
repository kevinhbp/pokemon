package id.co.app.nucocore.domain.network.nuco

import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.pokemon.PokemonList

interface MainClientContract {

  interface MainCommon {
    suspend fun getPokemonList(offset: Int, limit: Int): PokemonList
    suspend fun getPokemonDetail(id: Any): Pokemon
  }
}