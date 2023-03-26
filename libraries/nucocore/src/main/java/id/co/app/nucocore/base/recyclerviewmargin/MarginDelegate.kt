package id.co.app.nucocore.base.recyclerviewmargin

import android.graphics.Rect
import androidx.annotation.Px
import androidx.recyclerview.widget.OrientationHelper

class MarginDelegate(private val spanCount: Int, @Px private val spaceItem: Int) {
    fun calculateMargin(
        outRect: Rect,
        position: Int,
        spanCurrent: Int,
        itemCount: Int,
        orientation: Int,
        isReverse: Boolean,
        isRTL: Boolean
    ) {
        if (orientation == OrientationHelper.VERTICAL) {
            outRect.left = spanCurrent * spaceItem / spanCount
            outRect.right = spaceItem - (spanCurrent + 1) * spaceItem / spanCount
            if (isReverse) {
                if (position >= spanCount) outRect.bottom = spaceItem
            } else {
                if (position >= spanCount) outRect.top = spaceItem
            }
        } else if (orientation == OrientationHelper.HORIZONTAL) {
            outRect.top = spanCurrent * spaceItem / spanCount
            outRect.bottom = spaceItem - (spanCurrent + 1) * spaceItem / spanCount
            if (isReverse) {
                if (position >= spanCount) outRect.right = spaceItem
            } else {
                if (position >= spanCount) outRect.left = spaceItem
            }
        }
    }
}