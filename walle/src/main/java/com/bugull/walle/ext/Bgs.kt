package com.bugull.walle.ext

import android.view.View
import com.bugull.walle.util.ShadowUtils

/**
 * Author by xpl, Date on 2021/5/21.
 */

fun View.shadow(normal: Int, pressed: Int) {
    ShadowUtils.apply(
        this, ShadowUtils.Config()
            //.setCircle()
            .setShadowColor(normal, pressed)
    )
}

fun View.defaultShadow() = shadow(0xc0ff9988.toInt(), 0x60ffffff)