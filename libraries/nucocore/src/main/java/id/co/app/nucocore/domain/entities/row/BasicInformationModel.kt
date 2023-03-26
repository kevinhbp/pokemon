package id.co.app.nucocore.domain.entities.row

import id.co.app.nucocore.base.BaseModel
import id.co.app.nucocore.base.adapterdelegate.DelegateAdapterItem
import java.io.Serializable

data class BasicInformationModel (val mId: Int) : Serializable, DelegateAdapterItem, BaseModel() {

  var region: String = ""
  var nursery: String = ""
  var assessor: String = ""
  var assessmentDate: String = ""

  var assessorLabel: String = "Assessor"

  override val id: Any get() = "bi$mId"

  override fun equals(other: BaseModel): Boolean =
    (other is BasicInformationModel && other.mId == mId)

  override fun content(): Any {
    return "Basic Information: $mId, $region, $nursery, $assessor, $assessmentDate, $assessorLabel"
  }

  override fun id(): Any = "bi$mId"
}