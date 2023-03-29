package id.co.app.nucocore.domain.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.co.app.nucocore.domain.entities.pokemon.Pokemon

@Dao
interface PokemonDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertDataList(dataList: List<Pokemon>)

  @Query("SELECT * FROM pokemon")
  fun getPokemonList(): List<Pokemon>

  @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
  fun getPokemonList(offset: Int, limit: Int): List<Pokemon>

}