package id.co.app.profile.model

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class HeaderProfileModel(val mId: Int, val mName: String, val mLocation: String, val mPID: String) : Serializable, DelegateAdapterItem, BaseModel() {

  override val id: Any get() = "profileHeader"

  override fun equals(other: BaseModel): Boolean = (other is HeaderProfileModel &&
    other.mId == mId && other.mName == mName && other.mLocation == mLocation && other.mPID == mPID)

  override fun content(): Any = "ProfileHeader,$mName,$mLocation,$mPID."

  override fun id(): Any = "profileHeader"

  fun getTextInitial(): String {
    if (mName.isEmpty()) return ""
    val names = mName.split(' ')
    if (names.isEmpty()) return "-"
    val firstInitial = names[0][0]
    val lastInitial = names[names.size - 1][0]
    return "$firstInitial$lastInitial"
  }

  fun getTextLocation(): String = "Location: $mLocation"

  fun getTextPID(): String = "Pernr: $mPID"
}