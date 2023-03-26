package id.co.app.nucocore.domain.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.app.nucocore.domain.entities.pokemon.Pokemon

@Database(
  entities = [Pokemon::class], version = 1, exportSchema = false
)
@TypeConverters(value = [TypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

  abstract fun pokemonDao(): PokemonDao
}