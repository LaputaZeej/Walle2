package com.bugull.walle.action.content.logger

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bugull.walle.R

/**
 * Author by xpl, Date on 2021/5/20.
 */
class LogcatAdapterSave(private val list: MutableList<LogLine>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val filters: MutableList<Filter> = mutableListOf()
    private val showList: MutableList<LogLine> = list.toMutableList()
    private val lock: Any = Any()

    fun add(line: LogLine) {
        synchronized(filters) {
            if (list.size > MAX_SIZE) {
                list.removeAt(0)
                showList.clear()
                showList.addAll(list)
            }
            list.add(line)

            if (doFilter(line)) {
                showList.add(line)
            }
            notifyItemInserted(showList.size)
        }
    }

    fun updateFilters(f: List<Filter>) {
        this.filters.clear()
        this.filters.addAll(f)
        notifyShowList()
    }

    fun removeFilters() {
        this.filters.clear()
        notifyShowList()
    }

    fun addFilter(filter: Filter) {
        if (!filters.contains(filter)) {
            filters.add(filter)
        }
        notifyShowList()
    }

    fun removeFilter(filter: Filter) {
        if (filters.contains(filter)) {
            filters.remove(filter)
        }
        notifyShowList()
    }

    private fun doFilter(line: LogLine): Boolean {
        filters.forEach {
            val r = it.validate(line)
            if (!r) {
                return false
            }
        }
        return true
    }

    private fun notifyShowList() {
        showList.clear()
        val temp = if (filters.isEmpty()) {
            list
        } else {
            list.asSequence().filter {
                !doFilter(it)
            }.toList()
        }
        showList.addAll(temp)
        notifyDataSetChanged()
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val logLine = showList[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.tv_tag).apply {
                text = logLine.tag
                if (logLine.level == Log.WARN || logLine.level == Log.ERROR) {
                    setTextColor(LogLine.convertLogLevelToColor(logLine.level))
                } else {
                    setTextColor(Color.BLACK)
                }
            }
            findViewById<TextView>(R.id.tv_output).apply {
                text = logLine.output
                if (logLine.level == Log.WARN || logLine.level == Log.ERROR) {
                    setTextColor(LogLine.convertLogLevelToColor(logLine.level))
                } else {
                    setTextColor(Color.BLACK)
                }
            }
            findViewById<TextView>(R.id.tv_level).apply {
                text = LogLine.convertLogLevelToChar(logLine.level).toString()
                setBackgroundColor(LogLine.convertLogLevelToColor(logLine.level))
            }

            findViewById<TextView>(R.id.tv_pid).apply {
                text = logLine.processId.toString()
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
        return showList.size
    }

    companion object {
        private const val MAX_SIZE = 10000
    }
}