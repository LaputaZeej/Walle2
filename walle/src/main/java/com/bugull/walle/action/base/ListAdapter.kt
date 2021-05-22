package com.bugull.walle.action.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R
import com.bugull.walle.ext.logv
import java.lang.IllegalStateException

/**
 * Author by xpl, Date on 2021/5/18.
 */
abstract class ListAdapter<T>(
    val list: List<ListItem<T>>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.Item.type -> {
                ListViewHolder.ItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_info, null, false).apply {
                            layoutParams = RecyclerView.LayoutParams(
                                RecyclerView.LayoutParams.MATCH_PARENT,
                                RecyclerView.LayoutParams.WRAP_CONTENT
                            )
                        }
                )
            }
            ViewType.Click.type -> {
                ListViewHolder.ClickViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_click, null, false).apply {
                            layoutParams = RecyclerView.LayoutParams(
                                RecyclerView.LayoutParams.MATCH_PARENT,
                                RecyclerView.LayoutParams.WRAP_CONTENT
                            )
                        }
                )
            }

            ViewType.Bottom.type -> {
                ListViewHolder.BottomViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_click, null, false).apply {
                            layoutParams = RecyclerView.LayoutParams(
                                RecyclerView.LayoutParams.MATCH_PARENT,
                                RecyclerView.LayoutParams.WRAP_CONTENT
                            )
                        }
                )
            }
            else -> throw IllegalStateException("ListAdapter不支持的viewType:$viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1) {
            ViewType.Bottom.type
        } else {
            list[position].viewType.type
        }

    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) {
            0
        } else {
            list.size + 1
        }
    }

    internal sealed class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal class ItemViewHolder(itemView: View) : ListViewHolder(itemView)
        internal class ClickViewHolder(itemView: View) : ListViewHolder(itemView)
        internal class BottomViewHolder(itemView: View) : ListViewHolder(itemView)
    }
}

sealed class ViewType(val type: Int) {
    object Item : ViewType(0)
    object Click : ViewType(1)
    object Bottom : ViewType(2)
}

data class ListItem<T>(val data: T, val viewType: ViewType = ViewType.Item)