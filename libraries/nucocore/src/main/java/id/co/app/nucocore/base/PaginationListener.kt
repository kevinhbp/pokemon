package id.co.app.nucocore.base

import androidx.lifecycle.LiveData

interface PaginationListener {
    val isLoading: LiveData<Boolean>
    fun fetchPage(page: Int)
}