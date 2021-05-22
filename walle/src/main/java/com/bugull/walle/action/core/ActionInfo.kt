package com.bugull.walle.action.core

import android.content.Intent
import com.bugull.walle.Configuration

/**
 * Author by xpl, Date on 2021/5/6.
 */
data class ActionInfo(
    val clazz: Class<out AbsActionView>,
    val intent: Intent? = null,
    val single: Boolean = true
) {


}