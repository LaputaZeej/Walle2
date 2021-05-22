package com.bugull.walle.action.content.home

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R

/**
 * Author by xpl, Date on 2021/5/18.
 */
class GroupAdapter(
    private val list: List<Group>,
    space: Int,
    row: Int,
    click: (Group) -> Unit
) : HomeAdapter<Group>(R.layout.item_home_group, list, space, row, click) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val group = list[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.tv_group).setText(group.name)
            findViewById<ImageView>(R.id.iv_group).setImageResource(group.icon)
            setOnClickListener {
                click(group)
            }
        }
    }
}