package com.bugull.walle.action.content.logger

import android.os.Handler
import android.os.Message
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Author by xpl, Date on 2021/5/19.
 */


const val BUFFER_MAIN = "main"
const val BUFFER_SYSTEM = "system"

fun getLogcatArgs(buffer: String): List<String> {
    return mutableListOf<String>("logcat", "-v", "time").apply {
        if (BUFFER_MAIN != buffer) {
            add("-b")
            add(buffer)
        }
    }.toList()
}

fun getLogcatProcess(buffer: String): Process {
    return Runtime.getRuntime().exec(getLogcatArgs(buffer).toTypedArray())
}

fun getLastLogLine(buffer: String): String? {
    var process: Process? = null
    var reader: BufferedReader? = null
    var result: String? = null
    try {
        val logcatArgs = getLogcatArgs(buffer)
        logcatArgs.toMutableList().add("-d") // -d just dumps the whole thing
        process = Runtime.getRuntime().exec(logcatArgs.toTypedArray())
        reader = BufferedReader(InputStreamReader(process.inputStream), 8192)
        while (true) {
            val line = reader.readLine()
            if (line.isNullOrEmpty()) {
                result = line
            } else {
                break
            }
        }

    } catch (e: Throwable) {
        e.printStackTrace()
    } finally {
        process?.destroy()
    }
    return result
}

