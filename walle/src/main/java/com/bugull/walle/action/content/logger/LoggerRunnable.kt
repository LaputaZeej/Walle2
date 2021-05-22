package com.bugull.walle.action.content.logger

import android.os.Message
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

/**
 * Author by xpl, Date on 2021/5/19.
 */

class LoggerRunnable : Runnable {
    private val mHandler: LoggerManager.LoggerHandler = LoggerManager.LoggerHandler()
    var running: Boolean = true

    override fun run() {
        var process: Process? = null
        try {
            //process = Runtime.getRuntime().exec(arrayOf("logcat", "*:I *:S"))
            process = getLogcatProcess("")
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String
            while (running) {
                line = bufferedReader.readLine()
                if (line != null) {
                    val logging = LogLine.newLogLine(line, false)
                    logging?.let { _logLine ->
                        if (performFilter(_logLine)) {
                            mHandler.sendMessageDelayed(Message.obtain().apply {
                                obj = _logLine
                            }, DELAY)
                        }
                    }
                } else {
                    break
                }
            }
        } catch (e: Throwable) {
            //e.printStackTrace()
        } finally {
            process?.destroy()
        }
    }

    companion object {
        private const val DELAY = 0L
    }

    private fun performFilter(_logLine: LogLine): Boolean {
        filters.forEach {
            val r = it.validate(_logLine)
            if (!r) {
                return false
            }
        }
        return true
    }

    fun stop() {
        running = false
        mHandler.removeCallbacksAndMessages(null)
    }


    private val filters: MutableList<Filter> =
        mutableListOf<Filter>(TagFilter("zygote", true), ProcessFilter(android.os.Process.myPid()))

}

interface Filter {
    val reverse: Boolean
        get() = false
    fun validate(logLine: LogLine): Boolean
}

// 等于tag的LogLine
class TagFilter(var tag: String = "zygote", override val reverse: Boolean = false) : Filter {
    override fun validate(logLine: LogLine): Boolean {
        val r = tag.isEmpty() || logLine.tag.toLowerCase(Locale.getDefault())
            .contains(tag.toLowerCase(Locale.getDefault()))
        return if (reverse) !r else r

    }
}

// 等于等于level的LogLine
class LevelFilter(var level: Int = Log.VERBOSE, override val reverse: Boolean = false) : Filter {
    override fun validate(logLine: LogLine): Boolean {
        val r = level <= logLine.level
        return if (reverse) !r else r
    }
}

// 等于pid的LogLine
class ProcessFilter(
    private val pid: Int = android.os.Process.myPid(),
    override val reverse: Boolean = false
) : Filter {
    override fun validate(logLine: LogLine): Boolean {
        val r = pid == logLine.processId
        return if (reverse) !r else r
    }
}