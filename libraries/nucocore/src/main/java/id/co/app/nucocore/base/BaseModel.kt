package id.co.app.nucocore.base

abstract class BaseModel {
    abstract val id: Any
    abstract fun equals(other: BaseModel): Boolean
}