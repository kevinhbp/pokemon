package id.co.app.nucocore.base

class BaseLoadingModel(override val id: Any = "BASE_LOADING_MODEL") : BaseModel() {
    override fun equals(other: BaseModel): Boolean {
        return other is BaseLoadingModel
    }
}