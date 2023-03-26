package id.co.app.nucocore.base.recyclerviewmargin

import android.graphics.Rect
import android.view.View.SCROLLBARS_OUTSIDE_OVERLAY
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


abstract class BaseLayoutMargin(private val spanCount: Int, @Px private val spacing: Int) : ItemDecoration() {
    private val marginDelegate: MarginDelegate = MarginDelegate(spanCount, spacing)

    open fun setPadding(rv: RecyclerView, @Px margin: Int) {
        this.setPadding(rv, margin, margin, margin, margin)
    }

    open fun setPadding(
        rv: RecyclerView,
        @Px top: Int,
        @Px bottom: Int,
        @Px left: Int,
        @Px right: Int
    ) {
        rv.clipToPadding = false
        rv.scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        rv.setPadding(left, top, right, bottom)
    }

    fun getMarginDelegate(): MarginDelegate {
        return marginDelegate
    }

    fun calculateMargin(
        outRect: Rect,
        position: Int,
        spanCurrent: Int,
        itemCount: Int,
        orientation: Int,
        isReverse: Boolean,
        isRTL: Boolean
    ) {
        marginDelegate.calculateMargin(outRect, position, spanCurrent, itemCount, orientation, isReverse, isRTL)
    }

    fun getSpacing(): Int {
        return spacing
    }

    fun getSpanCount(): Int {
        return spanCount
    }

}