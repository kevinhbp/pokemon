package id.co.app.nucocore.domain.network.nuco

import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.pokemon.PokemonEvolution
import id.co.app.nucocore.domain.entities.pokemon.PokemonList

interface MainClientContract {

  interface MainCommon {
    suspend fun getPokemonList(offset: Int, limit: Int): PokemonList
    suspend fun getPokemonDetail(id: Any): Pokemon
    suspend fun getPokemonTypeList(): PokemonList
    suspend fun getPokemonEvolutionChain(id: Any): PokemonEvolution
  }
}