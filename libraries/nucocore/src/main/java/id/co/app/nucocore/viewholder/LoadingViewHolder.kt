package id.co.app.nucocore.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.app.nucocore.R
import id.co.app.nucocore.base.BaseLoadingModel
import id.co.app.nucocore.base.BaseViewHolder

class LoadingViewHolder (itemView: ViewGroup): BaseViewHolder<BaseLoadingModel>(LayoutInflater.from(itemView.context).inflate(
    LAYOUT, itemView, false)) {
    override fun bind(data: BaseLoadingModel) {}

    companion object{
        val LAYOUT = R.layout.item_loading
    }
}