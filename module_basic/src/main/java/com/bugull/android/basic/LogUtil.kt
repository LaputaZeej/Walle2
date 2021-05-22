package com.bugull.android.basic

import android.util.Log

/**
 * Author by xpl, Date on 2021/5/7.
 */

object LogUtil {

    var DEBUG = true

    fun v(tag: String, msg: String) {
        if (DEBUG) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (DEBUG) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (DEBUG) {
            Log.e(tag, msg)
        }
    }
}

