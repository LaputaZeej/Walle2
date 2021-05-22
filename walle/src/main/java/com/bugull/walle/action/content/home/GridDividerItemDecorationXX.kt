package com.bugull.walle.action.content.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Author by xpl, Date on 2021/5/18.
 */
class GridDividerItemDecorationXX(val context: Context) : RecyclerView.ItemDecoration() {

    var mDivider: Drawable

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(ATTRS)
        mDivider = obtainStyledAttributes.getDrawable(0)
            ?: throw IllegalStateException("无法获取mDivider")
        obtainStyledAttributes.recycle()
    }


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter?.itemCount ?: 0
        if (spanCount <= 0 || childCount <= 0) return
        when {
            isFirstColumn(parent, itemPosition, spanCount, childCount) -> {
                outRect.set(0, mDivider.intrinsicHeight, 0,0 )
            }
            isFirstRow(parent, itemPosition, spanCount, childCount) -> {
                outRect.set(mDivider.intrinsicWidth, 0, 0, 0)
            }
            isLastColumn(parent, itemPosition, spanCount, childCount) -> {
                outRect.set(0, 0, 0, mDivider.intrinsicHeight)
            }
            isLastRow(parent, itemPosition, spanCount, childCount) -> {
                outRect.set(0, 0, mDivider.intrinsicWidth, 0)
            }
        }
    }

    private fun getSpanCount(recycler: RecyclerView): Int {
        return when (val layoutManager = recycler.layoutManager) {
            is GridLayoutManager -> {
                layoutManager.spanCount
            }
            is StaggeredGridLayoutManager -> {
                layoutManager.spanCount
            }
            else -> {
                -1
            }
        }
    }

    private fun isFirstColumn(
        parent: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if (pos % spanCount == 0) {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if (pos % spanCount == 0) { // 第一列
                    return true
                }
            } else {
            }
        }
        return false
    }

    private fun isLastColumn(
        parent: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) { // 如果是最后一列，则不需要绘制右边
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) { // 最后一列
                    return true
                }
            } else {
            }
        }
        return false
    }

    private fun isFirstRow(
        parent: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            return pos / spanCount + 1 == 1
        } else if (layoutManager is StaggeredGridLayoutManager) {
        }
        return false
    }


    private fun isLastRow(
        parent: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val lines =
                if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            return lines == pos / spanCount + 1
        } else if (layoutManager is StaggeredGridLayoutManager) {
        }
        return false
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        (0 until childCount).forEach { i ->
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin
            val right = left + mDivider.intrinsicWidth
            val top = child.top - layoutParams.topMargin
            val bottom = child.bottom + layoutParams.bottomMargin
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }

    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        (0 until childCount).forEach { i ->
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.left - layoutParams.leftMargin
            val right = child.right + layoutParams.rightMargin + mDivider.intrinsicWidth
            val top = child.bottom + layoutParams.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    companion object {
        val ATTRS: IntArray = intArrayOf(android.R.attr.listDivider)
    }
}