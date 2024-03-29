package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeAbilityModel(
  val name: String,
  val hidden: Boolean,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = name

  override fun id(): Any = name

  override fun equals(other: BaseModel): Boolean {
    return other is PokeAbilityModel && other.name == name
  }

  override fun content(): Any {
    val isHidden = if (hidden) "hidden" else "not-hidden"
    return "pokemon_ability:$name.$isHidden"
  }
}