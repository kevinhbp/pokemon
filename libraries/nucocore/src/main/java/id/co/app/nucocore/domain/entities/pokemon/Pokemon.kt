package id.co.app.nucocore.domain.entities.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "pokemon")
data class Pokemon(
  @PrimaryKey(autoGenerate = false) val id: Int,
  val name: String,
  val types: List<PokeType>,
  val height: Double,
  val weight: Double,
  val sprites: PokeSprite,
  val stats: List<PokeStat>,
  val abilities: List<PokeAbility>,
  @SerializedName("base_experience")
  val baseExp: Int,
  val species: PokeResult,
  val photoFilePath: String?,
) : Serializable