package id.co.app.nucocore.base.recyclerviewmargin

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.*


class LayoutMarginDecoration : BaseLayoutMargin {
    constructor(@Px spacing: Int) : super(1, spacing)
    constructor(spanCount: Int, @Px spacing: Int) : super(spanCount, spacing)


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val isRTL = false
        var orientation = OrientationHelper.VERTICAL
        var isInverse = false
        var position = parent.getChildAdapterPosition(view)
        var spanCurrent = position % getSpanCount()
        if (parent.layoutManager is StaggeredGridLayoutManager) {
            orientation = (parent.layoutManager as StaggeredGridLayoutManager?)!!.orientation
            isInverse = (parent.layoutManager as StaggeredGridLayoutManager?)!!.reverseLayout
            val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            spanCurrent = lp.spanIndex
        } else if (parent.layoutManager is GridLayoutManager) {
            orientation = (parent.layoutManager as GridLayoutManager?)!!.orientation
            isInverse = (parent.layoutManager as GridLayoutManager?)!!.reverseLayout
            val lp = view.layoutParams as GridLayoutManager.LayoutParams
            spanCurrent = lp.spanIndex
            if (isRTL && orientation == OrientationHelper.VERTICAL) {
                spanCurrent = getSpanCount() - spanCurrent - 1
            }
        } else if (parent.layoutManager is LinearLayoutManager) {
            orientation = (parent.layoutManager as LinearLayoutManager?)!!.orientation
            isInverse = (parent.layoutManager as LinearLayoutManager?)!!.reverseLayout
            position = parent.getChildAdapterPosition(view) // item position
            spanCurrent = 0
        }
        if (isRTL && orientation == OrientationHelper.HORIZONTAL) {
            position = state.itemCount - position - 1
        }
        calculateMargin(
            outRect,
            position,
            spanCurrent,
            state.itemCount,
            orientation,
            isInverse,
            isRTL
        )
    }
}