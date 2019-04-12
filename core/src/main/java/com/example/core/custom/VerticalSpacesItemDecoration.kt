package com.example.core.custom

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalSpacesItemDecoration(
    val space: Int,
    private val isHorizontalOffset: Boolean = false,
    val column: Int = 1
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (isHorizontalOffset) {
            outRect.left = space
            outRect.right = space
        }
        outRect.bottom = space

        val currentPos = parent.getChildLayoutPosition(view)
        if (currentPos >= column) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}