package com.linhtt.photoeditor.custom

import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView

class ScrollPositionSnap() : LinearSnapHelper() {

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val position = layoutManager!!.getPosition(centerView)
        var targetPosition = -1
        if (layoutManager.canScrollHorizontally()) {
            targetPosition = if (velocityX < 0) {
                position - 1
            } else {
                position + 1
            }
        }

        if (layoutManager.canScrollVertically()) {
            targetPosition = if (velocityY < 0) {
                position - 1
            } else {
                position + 1
            }
        }

        val firstItem = 0
        val lastItem = layoutManager.itemCount - 1
        targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem))
        return targetPosition
    }


}