package id.co.app.nucocore.domain.entities.view

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class PokeHeaderModel(
  val count: Int,
): Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = count

  override fun id(): Any = count

  override fun equals(other: BaseModel): Boolean {
    return other is PokeHeaderModel && other.count == count
  }

  override fun content(): Any {
    return "pokemon_header:$count"
  }
}