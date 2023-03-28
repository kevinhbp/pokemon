package id.co.app.nucocore.domain.network.nuco

import id.co.app.nucocore.domain.entities.pokemon.Pokemon
import id.co.app.nucocore.domain.entities.pokemon.PokemonEvolution
import id.co.app.nucocore.domain.entities.pokemon.PokemonList
import retrofit2.http.*

interface MainService {

  @GET("pokemon")
  suspend fun getPokemonList(
    @Query("offset") offset: Int, @Query("limit") limit: Int
  ): PokemonList

  @GET("pokemon/{id}")
  suspend fun getPokemonDetail(
    @Path("id") id: Any
  ): Pokemon

  @GET("evolution-chain/{id}")
  suspend fun getPokemonEvolutionChain(
    @Path("id") id: Any
  ): PokemonEvolution
}