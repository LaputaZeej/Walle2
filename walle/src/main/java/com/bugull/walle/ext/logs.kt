package com.bugull.walle.ext

import android.util.Log
import com.bugull.android.basic.LogUtil
import com.bugull.walle.Configuration

/**
 * Author by xpl, Date on 2021/5/7.
 */

fun logv(any: Any?, tag: String = Configuration.TAG) {
    LogUtil.v(tag, "${any ?: "msg is null"}")
}

fun logd(any: Any?, tag: String = Configuration.TAG) {
    LogUtil.d(tag, "${any ?: "msg is null"}")
}

fun logi(any: Any?, tag: String = Configuration.TAG) {
    LogUtil.i(tag, "${any ?: "msg is null"}")
}

fun logw(any: Any?, tag: String = Configuration.TAG) {
    LogUtil.w(tag, "${any ?: "msg is null"}")
}

fun loge(any: Any?, tag: String = Configuration.TAG) {
    LogUtil.e(tag, "${any ?: "msg is null"}")
}

