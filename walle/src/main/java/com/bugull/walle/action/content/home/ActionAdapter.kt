package com.bugull.walle.action.content.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R
import java.util.*

/**
 * Author by xpl, Date on 2021/5/18.
 */
class ActionAdapter(
    private val list: List<Action>,
    space: Int,
    row: Int,
    click: (Action) -> Unit
) : HomeAdapter<Action>(R.layout.item_home_action, list, space, row, click) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val action = list[position]
        with(holder.itemView) {
            val iconView = findViewById<ImageView>(R.id.iv_action)
            val tvView = findViewById<TextView>(R.id.tv_action)
            tvView.setText(action.name)
            iconView.setImageResource(action.icon)
            when (action.step) {
                Action.Step.NAN -> {
                    iconView.setColorFilter(Color.GRAY)
                    tvView.setTextColor(Color.GRAY)
                }
                Action.Step.Release -> {
                    iconView.setColorFilter(Color.BLACK)
                    tvView.setTextColor(context.resources.getColor(R.color.black_mozi))
                    setOnClickListener {
                        click(action)
                    }
                }
                Action.Step.Dev -> {
                    iconView.setColorFilter(Color.BLUE)
                    tvView.setTextColor(Color.BLUE)
                    setOnClickListener {
                        click(action)
                    }
                }
            }


        }
    }


}