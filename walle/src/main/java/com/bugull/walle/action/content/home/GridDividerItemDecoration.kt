package com.bugull.walle.action.content.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bugull.walle.ext.logv
import java.lang.IllegalStateException

/**
 * Author by xpl, Date on 2021/5/18.
 */
class GridDividerItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

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
            isLastColumn(parent, itemPosition, spanCount, childCount) -> {
                if (itemPosition == childCount - 1) {
                    outRect.set(0, 0, 0, 0)
                } else {
                    outRect.set(0, 0, 0, mDivider.intrinsicHeight)
                }
            }
            isLastRow(parent, itemPosition, spanCount, childCount) -> {
                outRect.set(0, 0, mDivider.intrinsicWidth, 0)
            }
            else -> {
                outRect.set(0, 0, mDivider.intrinsicWidth, mDivider.intrinsicHeight)
            }
        }
        logv("xxxxxxxxxxxx getItemOffsets $outRect")
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

    private fun isLastColumn(
        recycler: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        return when (val layoutManager = recycler.layoutManager) {
            is GridLayoutManager -> {
                (pos + 1) % spanCount == 0
            }
            is StaggeredGridLayoutManager -> {
                if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                    (pos + 1) % spanCount == 0
                } else {
                    childCount - childCount % spanCount <= pos
                }
            }
            else -> {
                false
            }
        }
    }

    private fun isLastRow(
        recycler: RecyclerView,
        pos: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        return when (val layoutManager = recycler.layoutManager) {
            is GridLayoutManager -> {
                val last: Int = childCount % spanCount.let {
                    if (it == 0) {
                        spanCount
                    } else {
                        it
                    }
                }
                pos >= childCount - last
            }
            is StaggeredGridLayoutManager -> {
                if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                    val last: Int = childCount % spanCount.let {
                        if (it == 0) {
                            spanCount
                        } else {
                            it
                        }
                    }
                    pos >= childCount - last
                } else {
                    (pos + 1) % spanCount == 0
                }
            }
            else -> {
                false
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawHorizontal(c, parent)
        drawVertical(c, parent)

    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        logv("xxxxxxxxxxxx onDraw ${mDivider.bounds} childCount = $childCount")
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