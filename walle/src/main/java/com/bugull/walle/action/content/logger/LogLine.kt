package com.bugull.walle.action.content.logger

import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import java.util.regex.Pattern

/**
 * Author by xpl, Date on 2021/5/19.
 */
data class LogLine(
    val level: Int,
    val tag: String,
    val processId: Int,
    val timestamp: String,
    var expanded: Boolean=true,
    val output: String


) {
    companion object {
        private const val TIMESTAMP_LENGTH = 19
        private val logPattern = Pattern.compile(
            // log level
            // log level
            "(\\w)" +
                    "/" +  // tag
                    "([^(]+)" +
                    "\\(\\s*" +  // pid
                    "(\\d+)" +  // optional weird number that only occurs on ZTE blade
                    "(?:\\*\\s*\\d+)?" +
                    "\\): "
        )

        private const val filterPattern =
            "ResourceType|memtrack|android.os.Debug|BufferItemConsumer|DPM.*|MDM.*|ChimeraUtils|BatteryExternalStats.*|chatty.*|DisplayPowerController|WidgetHelper|WearableService|DigitalWidget.*|^ANDR-PERF-.*"

        fun newLogLine(originalLine: String, expanded: Boolean): LogLine? {
            var startIdx = 0
            var timestamp = ""
            var output = ""
            var level = -1
            var tag = ""
            var processId = -1
            // if the first char is a digit, then this starts out with a timestamp
            // otherwise, it's a legacy log or the beginning of the log output or something
            if (!TextUtils.isEmpty(originalLine)
                && Character.isDigit(originalLine[0])
                && originalLine.length >= TIMESTAMP_LENGTH
            ) {
                timestamp = originalLine.substring(0, TIMESTAMP_LENGTH - 1)
                startIdx = TIMESTAMP_LENGTH
            }

            val matcher = logPattern.matcher(originalLine)
            if (matcher.find(startIdx)) {
                val logLevelChar = matcher.group(1)[0]
                val logText = originalLine.substring(matcher.end())
                level = if (logText.matches(Regex("^maxLineHeight.*|Failed to read.*"))) {
                    convertCharToLogLevel('V')
                } else {
                    convertCharToLogLevel(logLevelChar)
                }

                val tagText = matcher.group(2) ?: ""
                if (tagText.matches(Regex(filterPattern))) {
                    level = convertCharToLogLevel('V')
                }

                tag = tagText
                processId = (matcher.group(3) ?: "-1").toInt()
                output = logText
            } else {
                output = originalLine
            }
            return LogLine(
                level = level,
                tag = tag,
                processId = processId,
                timestamp = timestamp,
                expanded = expanded,
                output = output
            )
        }


        private fun convertCharToLogLevel(logLevelChar: Char): Int {
            return when (logLevelChar) {
                'D' -> Log.DEBUG
                'E' -> Log.ERROR
                'I' -> Log.INFO
                'V' -> Log.VERBOSE
                'W' -> Log.WARN
                'F' -> Log.VERBOSE
                else -> -1
            }
        }

        fun convertLogLevelToChar(logLevel: Int): Char {
            return when (logLevel) {
                Log.DEBUG -> 'D'
                Log.ERROR -> 'E'
                Log.INFO -> 'I'
                Log.VERBOSE -> 'V'
                Log.WARN -> 'W'
                else -> ' '
            }
        }

        private val COLOR_ERROR = Color.parseColor("#E04644")
        private val COLOR_WARN = Color.parseColor("#FF9C00")
        private val COLOR_DEBUG = Color.parseColor("#FDE47F")
        private val COLOR_INFO = Color.parseColor("#B7D968")
        private val COLOR_VERBOSE = Color.parseColor("#555E7B")

        fun convertLogLevelToColor(logLevel: Int): Int {
            return when (logLevel) {
                Log.ERROR -> COLOR_ERROR
                Log.WARN -> COLOR_WARN
                Log.DEBUG -> COLOR_DEBUG
                Log.INFO -> COLOR_INFO
                Log.VERBOSE -> COLOR_VERBOSE
                else -> Color.WHITE
            }
        }
    }
}

val LogLine.formatStr:String
    get() ="[${timestamp}] [${processId}] [${tag}] [$level] $output"