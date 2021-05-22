package com.bugull.walle.action.content.basic.phone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R
import com.bugull.walle.ext.logv

/**
 * Author by xpl, Date on 2021/5/18.
 */
class PhoneAdapter(
    private val list: List<PhoneInfo>,
    private val click: ((PhoneInfo) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return SimpleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_phone_info, null, false).apply {
                    layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT
                    )
                }
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val phoneInfo = list[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_title).text = phoneInfo.title
            findViewById<TextView>(R.id.tv_value).apply {
                if (phoneInfo.value.isEmpty()) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    text = phoneInfo.value
                }
            }
            findViewById<TextView>(R.id.tv_desc).apply {
                if (phoneInfo.desc.isEmpty()) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    text = phoneInfo.desc
                }
            }
            setOnClickListener {
                click?.invoke(phoneInfo)
            }
        }
    }
}