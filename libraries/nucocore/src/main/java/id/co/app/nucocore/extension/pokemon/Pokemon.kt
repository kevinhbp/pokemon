@file:Suppress("unused")

package id.co.app.nucocore.extension.pokemon

import android.graphics.Color
import id.co.app.nucocore.R.drawable

fun String.formatName(): String {
  if (this.isEmpty()) return ""
  if (!this.contains(' ')) {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  }
  val names = this.split(' ')
  val result = StringBuffer()
  for (mName in names) {
    result.append(mName.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
  }
  return result.toString()
}

fun Int.formatId(): String {
  return this.toString().padStart(3, '0')
}

fun getPokeSpritesById(id: Int): String {
  return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$id.png"
}

object ColorPokemon {
  private val normal = "#dedede"
  private val fire = "#FF7847"
  private val fighting = "#EB4971"
  private val water = "#66b9ff"
  private val flying = "#A197E1"
  private val grass = "#3fd456"
  private val poison = "#ca58ec"
  private val electric = "#FFDB63"
  private val ground = "#A2735E"
  private val psychic = "#fb9d9a"
  private val rock = "#bbb5a8"
  private val ice = "#91d9cd"
  private val bug = "#A2B257"
  private val dragon = "#6526F1"
  private val ghost = "#b3b9d9"
  private val dark = "#535356"
  private val steel = "#688588"
  private val fairy = "#f1a6eb"

  private fun toMap(): Map<String, String> {
    return mapOf(
      Pair("normal", normal),
      Pair("fire", fire),
      Pair("fighting", fighting),
      Pair("water", water),
      Pair("flying", flying),
      Pair("grass", grass),
      Pair("poison", poison),
      Pair("electric", electric),
      Pair("ground", ground),
      Pair("psychic", psychic),
      Pair("rock", rock),
      Pair("ice", ice),
      Pair("bug", bug),
      Pair("dragon", dragon),
      Pair("ghost", ghost),
      Pair("dark", dark),
      Pair("steel", steel),
      Pair("fairy", fairy),
    )
  }

  fun get(propertyName: String): Int {
    val mapRep = toMap()
    var result: String = normal
    if (mapRep.containsKey(propertyName)) {
      result = mapRep[propertyName] ?: normal
    }
    return Color.parseColor(result)
  }
}

object ColorPokemonBg {
  private val normal = "#D2D2D2"
  private val fire = "#D5886D"
  private val fighting = "#D9798B"
  private val water = "#78BEFF"
  private val flying = "#C2BBEE"
  private val grass = "#95CE9E"
  private val poison = "#D695EE"
  private val electric = "#D0AC38"
  private val ground = "#A56757"
  private val psychic = "#B6726F"
  private val rock = "#E1DFDD"
  private val ice = "#C8F5E8"
  private val bug = "#B5C586"
  private val dragon = "#B094EF"
  private val ghost = "#B8BACC"
  private val dark = "#9C9CA1"
  private val steel = "#9BB1B6"
  private val fairy = "#C4A2C1"

  private fun toMap(): Map<String, String> {
    return mapOf(
      Pair("normal", normal),
      Pair("fire", fire),
      Pair("fighting", fighting),
      Pair("water", water),
      Pair("flying", flying),
      Pair("grass", grass),
      Pair("poison", poison),
      Pair("electric", electric),
      Pair("ground", ground),
      Pair("psychic", psychic),
      Pair("rock", rock),
      Pair("ice", ice),
      Pair("bug", bug),
      Pair("dragon", dragon),
      Pair("ghost", ghost),
      Pair("dark", dark),
      Pair("steel", steel),
      Pair("fairy", fairy),
    )
  }

  fun get(propertyName: String): Int {
    val mapRep = toMap()
    var result: String = normal
    if (mapRep.containsKey(propertyName)) {
      result = mapRep[propertyName] ?: normal
    }
    return Color.parseColor(result)
  }
}

object IconPokemonResId {
  private val normal = drawable.ic_normal
  private val fire = drawable.ic_fire
  private val fighting = drawable.ic_fighting
  private val water = drawable.ic_water
  private val flying = drawable.ic_flying
  private val grass = drawable.ic_grass
  private val poison = drawable.ic_poison
  private val electric = drawable.ic_electric
  private val ground = drawable.ic_ground
  private val psychic = drawable.ic_psychic
  private val rock = drawable.ic_rock
  private val ice = drawable.ic_ice
  private val bug = drawable.ic_bug
  private val dragon = drawable.ic_dragon
  private val ghost = drawable.ic_ghost
  private val dark = drawable.ic_dark
  private val steel = drawable.ic_steel
  private val fairy = drawable.ic_fairy

  private fun toMap(): Map<String, Int> {
    return mapOf(
      Pair("normal", normal),
      Pair("fire", fire),
      Pair("fighting", fighting),
      Pair("water", water),
      Pair("flying", flying),
      Pair("grass", grass),
      Pair("poison", poison),
      Pair("electric", electric),
      Pair("ground", ground),
      Pair("psychic", psychic),
      Pair("rock", rock),
      Pair("ice", ice),
      Pair("bug", bug),
      Pair("dragon", dragon),
      Pair("ghost", ghost),
      Pair("dark", dark),
      Pair("steel", steel),
      Pair("fairy", fairy),
    )
  }

  fun get(propertyName: String): Int {
    val mapRep = toMap()
    var result: Int = normal
    if (mapRep.containsKey(propertyName)) {
      result = mapRep[propertyName] ?: normal
    }
    return result
  }
}