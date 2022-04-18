package com.mahmoud_ashraf.menustask.core.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class ScrollingPagination(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (limitReached()) {
            loadMoreDate()
        }
    }

    private fun limitReached(): Boolean {
        val visibleItemCount: Int = linearLayoutManager.childCount
        val totalItemCount: Int = linearLayoutManager.itemCount
        val firstVisibleItemPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()

        return (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0

    }

    abstract fun loadMoreDate()

}