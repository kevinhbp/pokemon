package id.co.app.nucocore.domain.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import id.co.app.nucocore.domain.entities.pokemon.PokeAbility
import id.co.app.nucocore.domain.entities.pokemon.PokeResult
import id.co.app.nucocore.domain.entities.pokemon.PokeStat
import id.co.app.nucocore.domain.entities.pokemon.PokeType

@ProvidedTypeConverter
class TypeConverter constructor(
  private val moshi: Moshi
) {
  @TypeConverter
  fun fromPokeType(type: List<PokeType>?): String {
    val listType = Types.newParameterizedType(List::class.java, PokeType::class.java)
    val adapter: JsonAdapter<List<PokeType>> = moshi.adapter(listType)
    return adapter.toJson(type)
  }

  @TypeConverter
  fun toPokeType(value: String): List<PokeType>? {
    val listType = Types.newParameterizedType(List::class.java, PokeType::class.java)
    val adapter: JsonAdapter<List<PokeType>> = moshi.adapter(listType)
    return adapter.fromJson(value)
  }

  @TypeConverter
  fun fromPokeStat(type: List<PokeStat>?): String {
    val listType = Types.newParameterizedType(List::class.java, PokeStat::class.java)
    val adapter: JsonAdapter<List<PokeStat>> = moshi.adapter(listType)
    return adapter.toJson(type)
  }

  @TypeConverter
  fun toPokeStat(value: String): List<PokeStat>? {
    val listType = Types.newParameterizedType(List::class.java, PokeStat::class.java)
    val adapter: JsonAdapter<List<PokeStat>> = moshi.adapter(listType)
    return adapter.fromJson(value)
  }

  @TypeConverter
  fun fromPokeAbility(model: List<PokeAbility>?): String {
    val listType = Types.newParameterizedType(List::class.java, PokeAbility::class.java)
    val adapter: JsonAdapter<List<PokeAbility>> = moshi.adapter(listType)
    return adapter.toJson(model)
  }

  @TypeConverter
  fun toPokeAbility(value: String): List<PokeAbility>? {
    val listType = Types.newParameterizedType(List::class.java, PokeAbility::class.java)
    val adapter: JsonAdapter<List<PokeAbility>> = moshi.adapter(listType)
    return adapter.fromJson(value)
  }

  @TypeConverter
  fun fromPokeResult(type: PokeResult?): String {
    val adapter: JsonAdapter<PokeResult> = moshi.adapter(PokeResult::class.java)
    return adapter.toJson(type)
  }

  @TypeConverter
  fun toPokeResult(value: String): PokeResult? {
    val adapter: JsonAdapter<PokeResult> = moshi.adapter(PokeResult::class.java)
    return adapter.fromJson(value)
  }
}