package com.bugull.walle.action.content.logger

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R
import com.bugull.walle.ext.setTextStyle

/**
 * Author by xpl, Date on 2021/5/20.
 */
class LogcatAdapter(private val list: MutableList<LogLine> = mutableListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mFilterTag: String = ""

    fun setData(dataList: List<LogLine>, tag: String) {
        this.mFilterTag = tag
        list.clear()
        list.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData(logLine: LogLine) {
        list.add(logLine)
        notifyItemInserted(list.size)
    }

    class LogcatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LogcatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_logcat, null, false).apply {
                layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            }
        )
    }

    private fun TextView.setTextForFilterTag(text:String){
        setTextStyle(text,mFilterTag,this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val logLine = list[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.tv_tag).apply {
                //text = logLine.tag
                if (logLine.level == Log.WARN || logLine.level == Log.ERROR) {
                    setTextColor(LogLine.convertLogLevelToColor(logLine.level))
                } else {
                    setTextColor(Color.BLACK)
                }
                setTextForFilterTag(logLine.tag)
            }
            findViewById<TextView>(R.id.tv_output).apply {
                //text = logLine.output
                if (logLine.level == Log.WARN || logLine.level == Log.ERROR) {
                    setTextColor(LogLine.convertLogLevelToColor(logLine.level))
                } else {
                    setTextColor(Color.BLACK)
                }
                setTextForFilterTag(logLine.output)
            }
            findViewById<TextView>(R.id.tv_level).apply {
                text = LogLine.convertLogLevelToChar(logLine.level).toString()
                setBackgroundColor(LogLine.convertLogLevelToColor(logLine.level))
            }

            findViewById<TextView>(R.id.tv_pid).apply {
                //text = logLine.processId.toString()
                setTextForFilterTag(logLine.processId.toString())
            }

            findViewById<TextView>(R.id.tv_time).apply {
                text = logLine.timestamp
            }
            val expanded = logLine.expanded
            if (expanded) {
                setBackgroundColor(Color.BLACK)
                findViewById<TextView>(R.id.tv_pid).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tv_time).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tv_pid).setTextColor(Color.WHITE)
                findViewById<TextView>(R.id.tv_time).setTextColor(Color.WHITE)
                findViewById<TextView>(R.id.tv_output).setTextColor(Color.WHITE)
                findViewById<TextView>(R.id.tv_tag).setTextColor(Color.WHITE)
                findViewById<TextView>(R.id.tv_output).isSingleLine = false
            } else {
                setBackgroundColor(Color.TRANSPARENT)
                findViewById<TextView>(R.id.tv_pid).visibility = View.GONE
                findViewById<TextView>(R.id.tv_time).visibility = View.GONE
                findViewById<TextView>(R.id.tv_pid).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.tv_time).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.tv_output).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.tv_tag).setTextColor(Color.BLACK)
                findViewById<TextView>(R.id.tv_output).isSingleLine = true
            }

            setOnClickListener {
                logLine.expanded = !expanded
                notifyDataSetChanged()
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}