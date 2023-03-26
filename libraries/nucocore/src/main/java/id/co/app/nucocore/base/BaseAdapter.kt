package id.co.app.nucocore.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.app.nucocore.viewholder.LoadingViewHolder

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder<BaseModel>>(){
    protected var data = mutableListOf<BaseModel>()
    private val loadingModel = BaseLoadingModel()

    init {
        data.add(loadingModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if(data[position] == loadingModel) return LoadingViewHolder.LAYOUT
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseModel> {
        return if(viewType == LoadingViewHolder.LAYOUT) LoadingViewHolder(parent) as BaseViewHolder<BaseModel>
        else createViewHolders(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseModel>, position: Int) {
        holder.bind(data[position])
    }

    fun resetRecyclerView(){
        submitList(listOf(loadingModel))
    }

    fun showLoading(){
        if(!data.contains(loadingModel)) {
            val newList = this.data.toMutableList()
            newList.add(loadingModel)
            submitList(newList)
        }
    }

    fun appendList(data: List<BaseModel>){
        val newList = this.data.toMutableList()
        newList.remove(loadingModel)
        newList.addAll(data)
        submitList(newList)
    }

    fun submitList(data: List<BaseModel>){
        val oldData = this.data
        val callback = DiffUtil.calculateDiff(object: DiffUtil.Callback(){
            override fun getOldListSize(): Int {
                return oldData.size
            }

            override fun getNewListSize(): Int {
                return data.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldData[oldItemPosition].id == data[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldData[oldItemPosition].equals(data[newItemPosition])
            }
        })
        callback.dispatchUpdatesTo(this)
        this.data = data.toMutableList()
    }

    abstract fun createViewHolders(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseModel>
}