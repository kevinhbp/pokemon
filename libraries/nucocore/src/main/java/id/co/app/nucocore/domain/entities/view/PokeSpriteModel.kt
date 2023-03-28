package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeSpriteModel(
  val url: String,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = url

  override fun id(): Any = url

  override fun equals(other: BaseModel): Boolean {
    return other is PokeSpriteModel && other.url == url
  }

  override fun content(): Any {
    return "pokemon_sprite:$url"
  }
}