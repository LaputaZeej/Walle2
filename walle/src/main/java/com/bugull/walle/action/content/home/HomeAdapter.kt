package com.bugull.walle.action.content.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.ext.logv

/**
 * Author by xpl, Date on 2021/5/18.
 */
abstract class HomeAdapter<T>(
    @LayoutRes private val resId: Int,
    private val list: List<T>,
    private val space: Int,
    private val row: Int,
    val click: (T) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(resId, null, false)
        // TODO space 如何获取GridDividerItemDecoration的间隔 暂时从外部传进来
        val size = (0.5f + (parent.measuredWidth - (row - 1) * space * 1.0f) / row).toInt()
        logv("size = $size")
        view.layoutParams = ViewGroup.LayoutParams(size, size)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}