package id.co.app.nucocore.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T: BaseModel> (view: View): RecyclerView.ViewHolder(view){
    abstract fun bind(data: T)
}